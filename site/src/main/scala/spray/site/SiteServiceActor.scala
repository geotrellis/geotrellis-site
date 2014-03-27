/*
 * Copyright © 2011-2013 the spray project <http://spray.io>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spray.site

import akka.event.Logging._
import shapeless._
import spray.routing.directives.{ DirectoryListing, LogEntry }
import spray.httpx.marshalling.Marshaller
import spray.httpx.TwirlSupport._
import spray.http._
import spray.routing._
import html._
import StatusCodes._

import geotrellis._
import geotrellis.process
import geotrellis.source.ValueSource

class SiteServiceActor(settings: SiteSettings) extends HttpServiceActor {

  // format: OFF
  def receive = runRoute {
    dynamicIf(settings.devMode) { // for proper support of twirl + sbt-revolver during development
      (get & compressResponse()) {
        host("geotrellis.io", "localhost", "127.0.0.1") {
          path("favicon.ico") {
            getFromResource("theme/favicon.ico/")
          } ~
          path("favicon.png") {
            complete(NotFound) // fail early in order to prevent error response logging
          } ~
          pathPrefix("gt") {
            demoRoute
          } ~
          logRequestResponse(showErrorResponses _) {
            getFromResourceDirectory("theme") ~
            pathPrefix("_images") {
              getFromResourceDirectory("sphinx/json/_images")
            } ~
            logRequest(showRequest _) {
              pathSingleSlash {
                complete(page(home()))
              } ~
              pathPrefix("documentation" / Segment / "api") { version =>
                val dir = s"api/$version/"
                pathEnd {
                  redirect(s"/documentation/$version/api/", MovedPermanently)
                } ~
                pathSingleSlash {
                  getFromResource(dir + "index.html")
                } ~
                getFromResourceDirectory(dir)
              } ~
              pathSuffixTest(Slash) {
                path("home" /) {
                  redirect("/", MovedPermanently)
                } ~
                path("index" /) {
                  complete(page(index()))
                } ~
                pathPrefixTest("documentation" / !IntNumber ~ !PathEnd ~ Rest) { subUri =>
                  redirect("/documentation/" + Main.settings.mainVersion + '/' + subUri, MovedPermanently)
                } ~
                requestUri { uri =>
                  val path = uri.path.toString
                  "-RC[123]/".r.findFirstIn(path) match {
                    case Some(found) => redirect(uri.withPath(Uri.Path(path.replace(found, "-RC4/"))), MovedPermanently)
                    case None => reject
                  }
                } ~
                sphinxNode { node =>
                  complete(page(document(node), node))
                }
              } ~
              unmatchedPath { ump =>
                redirect(ump.toString + "/", MovedPermanently)
              }
            }
          }
        } ~
        unmatchedPath { ump =>
          redirect("http://spray.io" + ump, Found)
        }
      }
    }
  }

  val demoRoute =  path("weighted-overlay"/) {
    parameters('weight1.as[Int], 'weight2.as[Int]) { (w1, w2) =>
      val layers = List("LAYER_1", "LAYER_2")
      val weights = List(w1,w2)

      val model = Model.weightedOverlay(layers,weights, None)
      val png:ValueSource[Png] = model.renderPng()

      png.run match {
        case process.Complete(img,h) =>
          respondWithMediaType(MediaTypes.`image/png`) {
            complete(img)
          }
        case process.Error(message,trace) =>
          println(message)
          println(trace)

          failWith(new RuntimeException(message))
      }
    }
  } ~
  unmatchedPath { ump => //anything hitting the demo subroute will have standard 404
    complete(StatusCodes.NotFound)
  }
  // format: ON

  val sphinxNode = path(Rest).map(Main.root.find).flatMap[ContentNode :: HNil] {
    case None       ⇒ complete(NotFound, page(error404()))
    case Some(node) ⇒ provide(node)
  }

  def showRequest(request: HttpRequest) = LogEntry(request.uri, InfoLevel)

  def showErrorResponses(request: HttpRequest): Any ⇒ Option[LogEntry] = {
    case HttpResponse(OK, _, _, _)       ⇒ None
    case HttpResponse(NotFound, _, _, _) ⇒ Some(LogEntry("404: " + request.uri, WarningLevel))
    case r @ HttpResponse(Found | MovedPermanently, _, _, _) ⇒
      Some(LogEntry(s"${r.status.intValue}: ${request.uri} -> ${r.header[HttpHeaders.Location].map(_.uri.toString).getOrElse("")}", WarningLevel))
    case response ⇒ Some(
      LogEntry("Non-200 response for\n  Request : " + request + "\n  Response: " + response, WarningLevel))
  }

  def showRepoResponses(repo: String)(request: HttpRequest): HttpResponsePart ⇒ Option[LogEntry] = {
    case HttpResponse(OK, _, _, _) ⇒ Some(LogEntry(repo + " 200: " + request.uri, InfoLevel))
    case ChunkedResponseStart(HttpResponse(OK, _, _, _)) ⇒ Some(LogEntry(repo + " 200 (chunked): " + request.uri, InfoLevel))
    case HttpResponse(NotFound, _, _, _) ⇒ Some(LogEntry(repo + " 404: " + request.uri))
    case _ ⇒ None
  }

  implicit val ListingMarshaller: Marshaller[DirectoryListing] =
    Marshaller.delegate(MediaTypes.`text/html`) { (listing: DirectoryListing) ⇒
      listing.copy(
        files = listing.files.filterNot(file ⇒
          file.getName.startsWith(".") || file.getName.startsWith("archetype-catalog")))
    }(DirectoryListing.DefaultMarshaller)
}
