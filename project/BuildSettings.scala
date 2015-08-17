import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import sbtassembly.Plugin.AssemblyKeys._
import sbtassembly.Plugin._
import sbtunidoc.Plugin._
import sbtunidoc.Plugin.UnidocKeys._
import spray.revolver.RevolverPlugin.Revolver
import twirl.sbt.TwirlPlugin.Twirl
import com.typesafe.sbt.osgi.SbtOsgi
import SbtOsgi._

object BuildSettings {
  val VERSION = "0.9.0"

  lazy val basicSettings = seq(
    version               := VERSION,
    homepage              := Some(new URL("http://spray.io")),
    organization          := "io.spray",
    organizationHomepage  := Some(new URL("http://spray.io")),
    description           := "A suite of lightweight Scala libraries for building and consuming RESTful " +
                             "web services on top of Akka",
    startYear             := Some(2011),
    licenses              := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scalaVersion          := "2.10.3",
    resolvers             ++= Dependencies.resolutionRepos,
    scalacOptions         := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.7",
      "-language:_",
      "-Xlog-reflective-calls"
    )
  )

  lazy val noPublishing = seq(
    publish := (),
    publishLocal := ()
  )

  lazy val generateSprayVersionConf = TaskKey[Seq[File]]("generate-spray-version-conf",
    "Create a reference.conf file in the managed resources folder that contains a spray.version = ... setting")

  lazy val sprayVersionConfGeneration = seq(
    (unmanagedResources in Compile) <<= (unmanagedResources in Compile).map(_.filter(_.getName != "reference.conf")),
    resourceGenerators in Compile <+= generateSprayVersionConf,
    generateSprayVersionConf <<= (unmanagedResourceDirectories in Compile, resourceManaged in Compile, version) map {
      (sourceDir, targetDir, version) => {
        val source = sourceDir / "reference.conf"
        val target = targetDir / "reference.conf"
        val conf = IO.read(source.get.head)
        IO.write(target, conf.replace("<VERSION>", version))
        Seq(target)
      }
    }
  )

  lazy val siteSettings = basicSettings ++ formatSettings ++ noPublishing ++ Twirl.settings ++ Revolver.settings ++
    SiteSupport.settings ++ seq(
      resourceGenerators in Compile <+= (target in ScalaUnidoc in unidoc in LocalRootProject){ docsLocation =>
        constant(Seq(docsLocation)).map(_.flatMap(_.***.get))
      },
      fork := true,
      assembly <<= assembly.dependsOn(unidoc in Compile in LocalRootProject)
    )

  lazy val docsSettings = basicSettings ++ noPublishing ++ seq(
    unmanagedSourceDirectories in Test <<= baseDirectory { _ ** "code" get }
  )

  lazy val exampleSettings = basicSettings ++ noPublishing
  lazy val standaloneServerExampleSettings = exampleSettings ++ Revolver.settings

  lazy val benchmarkSettings = basicSettings ++ noPublishing ++ Revolver.settings ++ assemblySettings ++ Seq(
    mainClass in assembly := Some("spray.examples.Main"),
    jarName in assembly := "benchmark.jar",
    test in assembly := {},
    javaOptions in Revolver.reStart ++= Seq("-verbose:gc", "-XX:+PrintCompilation")
  )

  import com.earldouglas.xsbtwebplugin.WebPlugin._
  lazy val jettyExampleSettings = exampleSettings ++ webSettings // ++ disableJettyLogSettings

  import com.earldouglas.xsbtwebplugin.PluginKeys._
  lazy val disableJettyLogSettings = inConfig(container.Configuration) {
    seq(
      start <<= (state, port, apps, customConfiguration, configurationFiles, configurationXml) map {
        (state, port, apps, cc, cf, cx) =>
          state.get(container.attribute).get.start(port, None, Utils.NopLogger, apps, cc, cf, cx)
      }
    )
  }

  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formattingPreferences,
    ScalariformKeys.preferences in Test    := formattingPreferences
  )

  import scalariform.formatter.preferences._
  def formattingPreferences =
    FormattingPreferences()
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DoubleIndentClassDeclaration, true)

  def osgiSettings(exports: Seq[String], imports: Seq[String] = Seq.empty) =
    SbtOsgi.osgiSettings ++ Seq(
      OsgiKeys.exportPackage := exports map { pkg => pkg + ".*;version=\"${Bundle-Version}\"" },
      OsgiKeys.importPackage <<= scalaVersion { sv => Seq("""scala.*;version="$<range;[==,=+);%s>"""".format(sv)) },
      OsgiKeys.importPackage ++= imports,
      OsgiKeys.importPackage += "akka.io.*;version=\"${Bundle-Version}\"",
      OsgiKeys.importPackage += "akka.spray.*;version=\"${Bundle-Version}\"",
      OsgiKeys.importPackage += """akka.*;version="$<range;[==,=+);$<@>>"""",
      OsgiKeys.importPackage += "*",
      OsgiKeys.additionalHeaders := Map("-removeheaders" -> "Include-Resource,Private-Package")
    )
}
