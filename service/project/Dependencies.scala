import sbt._

object Dependencies {

  val resolutionRepos = Seq(
    "spray repo" at "http://repo.spray.io/",
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    "Geotools" at "http://download.osgeo.org/webdav/geotools/"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  val gt            = "com.azavea.geotrellis"                  %%  "geotrellis"            % "0.9.0-RC4"
  val gtServices    = "com.azavea.geotrellis"                  %%  "geotrellis-services"   % "0.9.0-RC4"
  val gtGeotools    = "com.azavea.geotrellis"                  %%  "geotrellis-geotools"   % "0.9.0-RC4"
  val gtJetty       = "com.azavea.geotrellis"                  %%  "geotrellis-jetty"      % "0.9.0-RC4"

  val scalaReflect  = "org.scala-lang"                          %   "scala-reflect"         % "2.10.3"
  val akkaActor     = "com.typesafe.akka"                       %%  "akka-actor"            % "2.2.3"
  val akkaSlf4j     = "com.typesafe.akka"                       %%  "akka-slf4j"            % "2.1.4"
  val akkaTestKit   = "com.typesafe.akka"                       %%  "akka-testkit"          % "2.1.4"
  val parboiled     = "org.parboiled"                           %%  "parboiled-scala"       % "1.1.6"
  val shapeless     = "com.chuusai"                             %%  "shapeless"             % "1.2.4"
  val scalatest     = "org.scalatest"                           %%  "scalatest"             % "1.9.1"
  val specs2        = "org.specs2"                              %%  "specs2"                % "2.2.3"
  val sprayJson     = "io.spray"                                %%  "spray-json"            % "1.2.5"
  val sprayCaching  = "io.spray"                                %   "spray-caching"         % "1.2.0"
  val sprayCan      = "io.spray"                                %   "spray-can"             % "1.2.0"
  val sprayRouting  = "io.spray"                                %   "spray-routing"         % "1.2.0"

  val logback       = "ch.qos.logback"                          %   "logback-classic"       % "1.0.13"
  val mimepull      = "org.jvnet.mimepull"                      %   "mimepull"              % "1.9.3"
  val liftJson      = "net.liftweb"                             %%  "lift-json"             % "2.5.1"
  val json4sNative  = "org.json4s"                              %%  "json4s-native"         % "3.2.5"
  val json4sJackson = "org.json4s"                              %%  "json4s-jackson"        % "3.2.5"
  val playJson      = "com.typesafe.play"                       %%  "play-json"             % "2.2.0"
}
