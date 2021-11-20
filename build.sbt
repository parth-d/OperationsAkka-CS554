name := "OperationsAkka"

version := "0.1"

scalaVersion := "2.13.7"

val AkkaVersion = "2.6.17"
val ConfigVersion = "1.4.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
libraryDependencies += "com.typesafe" % "config" % ConfigVersion