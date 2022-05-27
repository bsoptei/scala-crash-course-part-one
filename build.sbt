import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "scala-crash-course-part-one",
    libraryDependencies ++= Seq(
      scalaTest % Test
    )
  )