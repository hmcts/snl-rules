lazy val root = Project("gatling-snl-rules", file("."))
  .enablePlugins(GatlingPlugin)
  .settings(buildSettings: _*)
  .settings(libraryDependencies ++= projectDependencies)
  .settings(scalariformSettings: _*)

lazy val buildSettings = Seq(
  organization := "uk.gov.hmcts.reform",
  version := "0.1.0",
  scalaVersion := "2.12.4"
)

scalaSource in Compile := baseDirectory.value / "simulations"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

lazy val projectDependencies = Seq(
//  "org.scalatest"                   %% "scalatest"                  % "3.0.4"          % "test,it",
  "io.gatling.highcharts"           % "gatling-charts-highcharts"   % "2.3.1"          % "compile, test,it",
  "io.gatling"                      % "gatling-test-framework"      % "2.3.1"          % "compile, test,it",
  "io.jsonwebtoken"                 % "jjwt-api"                    % "0.10.2"         % "compile, test,it",
  "io.jsonwebtoken"                 % "jjwt-impl"                   % "0.10.2"         % "compile, test,it"
)
