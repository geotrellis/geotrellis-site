import sbt._
import Keys._
import com.typesafe.sbt.osgi.SbtOsgi._

object Build extends Build {
  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }

  lazy val root = Project("root",file("."))
    .aggregate(docs, srv)
    .settings(basicSettings: _*)
    .settings(noPublishing: _*)



  lazy val srv = Project("srv", file("srv"))
    .settings(siteSettings: _*)
    .settings(resolvers ++= resolutionRepos)
    .settings(javaOptions += "-Xmx4G")
    .settings(libraryDependencies ++=
      compile(akkaActor, sprayJson, sprayCan, sprayCaching, sprayRouting, gt, gtServices) ++
      runtime(akkaSlf4j, logback) ++
      test(specs2)
    )

  lazy val docs = Project("docs", file("docs"))
    .settings(docsSettings: _*)
    .settings(libraryDependencies ++= test(akkaActor, sprayJson, gtGeotools,gtJetty))
}
