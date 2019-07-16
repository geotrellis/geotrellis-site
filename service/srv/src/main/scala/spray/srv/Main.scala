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

import akka.actor.{ ActorSystem, Props }
import akka.event.Logging
import akka.io.IO
import spray.can.Http
import spray.http.StringRendering
import geotrellis.source.RasterSource
import geotrellis.{ io, TypeByte, RasterExtent }
import geotrellis.data.arg.ArgWriter
import geotrellis.raster.op.transform.Resize
import geotrellis._

object Main extends App {
  implicit val system = ActorSystem("site")
  val log = Logging(system, getClass)
  val settings = SiteSettings(system)

  log.info("Starting service actor and HTTP server...")
  val service = system.actorOf(Props(new SiteServiceActor(settings)), "site-service")
  IO(Http) ! Http.Bind(service, settings.interface, settings.port)
}
