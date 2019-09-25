ThisBuild / organization := "com.avast"
ThisBuild / homepage := Some(url("https://github.com/avast/scala-server-toolkit"))
ThisBuild / description := "Functional programming toolkit for building server applications in Scala."
ThisBuild / licenses := Seq("MIT" -> url("https://raw.githubusercontent.com/avast/scala-server-toolkit/master/LICENSE"))
ThisBuild / developers := List(Developer("jakubjanecek", "Jakub Janecek", "janecek@avast.com", url("https://www.avast.com")))
ThisBuild / scmInfo := Some(
  ScmInfo(url("https://github.com/avast/scala-server-toolkit"), "scm:git:git@github.com:avast/scala-server-toolkit.git")
)

ThisBuild / scalaVersion := "2.12.10"
ThisBuild / scalacOptions := ScalacOptions.default

ThisBuild / turbo := true

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    compilerPlugin(Dependencies.kindProjector),
    Dependencies.catsEffect,
    Dependencies.logbackClassic % Test,
    Dependencies.scalaTest % Test
  ),
  Test / publishArtifact := false
)

lazy val root = project
  .in(file("."))
  .aggregate(example, http4sClient, http4sServer, jvmExecution, jvmSsl, jvmSystem, pureconfig)
  .settings(
    name := "scala-server-toolkit",
    publish / skip := true
  )

lazy val example = project
  .dependsOn(jvmExecution, http4sClient, http4sServer, jvmSsl, jvmSystem, pureconfig)
  .enablePlugins(MdocPlugin)
  .settings(
    commonSettings,
    name := "scala-server-toolkit-example",
    publish / skip := true,
    run / fork := true,
    Global / cancelable := true,
    mdocIn := baseDirectory.value / "src" / "main" / "mdoc",
    mdocOut := baseDirectory.value / ".." / "docs",
    libraryDependencies ++= Seq(
      Dependencies.zio,
      Dependencies.zioInteropCats
    )
  )

lazy val http4sClient = project
  .in(file("http4s-client"))
  .dependsOn(jvmSsl)
  .settings(commonSettings)
  .settings(
    name := "scala-server-toolkit-http4s-client",
    libraryDependencies += Dependencies.http4sBlazeClient
  )

lazy val http4sServer = project
  .in(file("http4s-server"))
  .dependsOn(http4sClient % Test)
  .settings(commonSettings)
  .settings(
    name := "scala-server-toolkit-http4s-server",
    libraryDependencies ++= Seq(
      Dependencies.http4sBlazeServer,
      Dependencies.http4sDsl,
      Dependencies.slf4jApi
    )
  )

lazy val jvmExecution = project
  .in(file("jvm-execution"))
  .settings(
    commonSettings,
    name := "scala-server-toolkit-jvm-execution",
    libraryDependencies += Dependencies.slf4jApi
  )

lazy val jvmSsl = project
  .in(file("jvm-ssl"))
  .settings(
    commonSettings,
    name := "scala-server-toolkit-jvm-ssl"
  )

lazy val jvmSystem = project
  .in(file("jvm-system"))
  .settings(
    commonSettings,
    name := "scala-server-toolkit-jvm-system"
  )

lazy val pureconfig = project
  .settings(
    commonSettings,
    name := "scala-server-toolkit-pureconfig",
    libraryDependencies += Dependencies.pureConfig
  )
