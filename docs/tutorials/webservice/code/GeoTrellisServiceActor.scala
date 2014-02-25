package tutorial

import akka.actor._

class GeoTrellisServiceActor extends GeoTrellisService with Actor {
  // the HttpService trait (which GeoTrellisService will extend) defines
  // only one abstract member, which connects the services environment
  // to the enclosing actor or test.
  def actorRefFactory = context

  def receive = runRoute(rootRoute)
}