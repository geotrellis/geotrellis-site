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
import geotrellis.services.ColorRampMap
import geotrellis.render.ColorRamps

class SiteServiceActor(settings: SiteSettings) extends HttpServiceActor {

  // format: OFF
  def receive = runRoute {
    dynamicIf(settings.devMode) {
      // for proper support of twirl + sbt-revolver during development
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
                    pathPrefix("documentation" / Segment / "api") {
                      version =>
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
                        pathPrefixTest("documentation" / !IntNumber ~ !PathEnd ~ Rest) {
                          subUri =>
                            redirect("/documentation/" + Main.settings.mainVersion + '/' + subUri, MovedPermanently)
                        } ~
                        requestUri {
                          uri =>
                            val path = uri.path.toString
                            "-RC[123]/".r.findFirstIn(path) match {
                              case Some(found) => redirect(uri.withPath(Uri.Path(path.replace(found, "-RC4/"))), MovedPermanently)
                              case None => reject
                            }
                        } ~
                        sphinxNode {
                          node =>
                            complete(page(document(node), node))
                        }
                    } ~
                    unmatchedPath {
                      ump =>
                        redirect(ump.toString + "/", MovedPermanently)
                    }
                }
            }
        } ~
          unmatchedPath {
            ump =>
              redirect("http://spray.io" + ump, Found)
          }
      }
    }
  }

  /*
  SERVICE=WMS&
  REQUEST=GetMap&
  VERSION=1.1.1&
  LAYERS=pop_density&
  STYLES=&
  FORMAT=image%2Fjpeg&
  TRANSPARENT=false&
  HEIGHT=256&
  WIDTH=256&
  WEIGHTS=1&
  COLORRAMP=red-to-blue&
  SRS=EPSG%3A3857&
  BBOX=-8414188.073632201,4852834.051769271,-8375052.315150191,4891969.810251278
   */

  val demoRoute = path("weighted-overlay") {
    parameters(
      'SERVICE,
      'REQUEST,
      'VERSION,
      'FORMAT ? "",
      'BBOX,
      'HEIGHT.as[Int],
      'WIDTH.as[Int],
      'LAYERS,
      'WEIGHTS,
      'PALETTE ? "ff0000,ffff00,00ff00,0000ff",
      'COLORS.as[Int] ? 4,
      'BREAKS ? "0,10,20,30,40,50,60,70,80,90",
      'COLORRAMP ? "colorRamp",
      'MASK ? "",
      'SRS ? "",
      'STYLES ? ""
    ) {
      (_, _, _, _, bbox, cols, rows, layersString, weightsString,
       palette, colors, breaksString, colorRamp, mask, srs, styles) => {
        val extent = Extent.fromString(bbox)
        val re = RasterExtent(extent, cols, rows)
        val layers = layersString.split(",")
        val weights = weightsString.split(",").map(_.toInt)
        val model = Model.weightedOverlay(layers, weights, Some(re))
        val breaks = breaksString.split(",").map(_.toInt)

        val ramp = {
          val cr = ColorRampMap.getOrElse(colorRamp,ColorRamps.BlueToRed)
          if(cr.toArray.length < breaks.length) { cr.interpolate(breaks.length) }
          else { cr }
        }

        val png:ValueSource[Png] =
          model.renderPng(ramp, breaks)

        png.run match {
          case process.Complete(img, h) =>
            respondWithMediaType(MediaTypes.`image/png`) {
              complete(img)
            }
          case process.Error(message, trace) =>
            println(message)
            println(trace)
            println(re)

            failWith(new RuntimeException(message))
        }
      }
    }
  } ~
  path("breaks") {
    parameters('layers,
      'weights,
      'numBreaks.as[Int],
      'mask ? "") {
      (layersParam,weightsParam,numBreaks,mask) => {
        val layers = layersParam.split(",")
        val weights = weightsParam.split(",").map(_.toInt)


        Model.weightedOverlay(layers,weights,None)
          .classBreaks(numBreaks)
          .run match {
          case process.Complete(breaks, _) =>
            val breaksArray = breaks.mkString("[", ",", "]")
            val json = s"""{ "classBreaks" : $breaksArray }"""
            complete(json)
          case process.Error(message,trace) =>
            failWith(new RuntimeException(message))
        }
      }
    }
  } ~
  path("ping"){
    complete("PONG")
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
