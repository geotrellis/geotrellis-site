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

  //  val rsNames = List("short_philly_bars", "short_philly_grocery_stores", "short_philly_rail_stops")
  //  val rs = rsNames.map { name ⇒
  //    server.get(Resize(io.LoadFile("site/data/walkshed/" + name + ".arg"), 2000, 2000))
  //  }
  //
  //  val writer = new ArgWriter(TypeByte)
  //  writer.write("philly_bars", rs(0), "philly_bars")
  //  writer.write("philly_grocery_stores", rs(1), "philly_grocery_stores")
  //  writer.write("philly_rail_stops", rs(2), "philly_rail_stops")
  // var rs = server.get(Resize(io.LoadFile("data/hillshade/hills.arg"), 1052, 724))
  // val writer = new ArgWriter(TypeByte)
  // writer.write("hills_small", rs, "hills_small")

  implicit val system = ActorSystem("site")
  val log = Logging(system, getClass)
  val settings = SiteSettings(system)

  log.info("Loading sphinx content root...")
  val root = new RootNode(SphinxDoc.load("index/").getOrElse(sys.error("index doc not found")))

  log.info("Starting service actor and HTTP server...")
  val service = system.actorOf(Props(new SiteServiceActor(settings)), "site-service")
  IO(Http) ! Http.Bind(service, settings.interface, settings.port)
}
