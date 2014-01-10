import sbt._
import Keys._
import com.typesafe.sbt.osgi.SbtOsgi._
import sbtunidoc.Plugin._
import UnidocKeys._

object Build extends Build {
  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }

  lazy val root = Project("root",file("."))
    .aggregate(docs, site)
    .settings(basicSettings: _*)
    .settings(noPublishing: _*)
    .settings(unidocSettings: _*)



  lazy val site = Project("site", file("site"))
    .settings(siteSettings: _*)
    .settings(SphinxSupport.settings: _*)
    .settings(resolvers ++= resolutionRepos)
    .settings(libraryDependencies ++=
      compile(akkaActor, sprayJson, sprayCan, sprayCaching, sprayRouting) ++
      runtime(akkaSlf4j, logback) ++
      test(specs2)
    )

  lazy val docs = Project("docs", file("docs"))
    .settings(docsSettings: _*)
    .settings(libraryDependencies ++= test(akkaActor, sprayJson, gtGeotools,gtJetty))


}
