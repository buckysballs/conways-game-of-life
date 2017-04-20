name := "conways-game-of-life"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.12" % "3.0.1",
  "org.json4s" %% "json4s-native" % "3.5.1"
)

mainClass in (Compile, packageBin) := Some("ConwaysGameOfLife")