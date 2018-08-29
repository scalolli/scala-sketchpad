val Http4sVersion = "0.18.16"
val Specs2Version = "4.2.0"
val LogbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "scala-sketchpad",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.6",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe" %% "circe-generic" % "0.10.0-M1",
      "io.circe"        %% "circe-literal" % "0.9.2",
      "org.scalatest"          %% "scalatest"          % "3.0.5"          % "test" withSources (),
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion
    )
  )

