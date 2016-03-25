resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= Seq(
  "com.decodified" % "scala-ssh" % "0.6.2",
  "org.bouncycastle" % "bcprov-jdk16" % "1.46",
  "com.jcraft" % "jzlib" % "1.1.1"
)

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.4.1")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

addSbtPlugin("io.spray" % "sbt-boilerplate" % "0.5.1")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.4.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-osgi" % "0.7.0")
