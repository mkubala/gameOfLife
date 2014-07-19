import sbt._
import sbt.Keys._

object GameoflifeBuild extends Build {

  lazy val gameoflife = Project(
    id = "gameoflife",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "gameOfLife",
      organization := "pl.mkubala",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      scalacOptions += "-deprecation"
    )
  )
}
