name := "OperationsAkka"

version := "0.1"

scalaVersion := "2.13.7"

val AkkaVersion = "2.6.11"
val ConfigVersion = "1.4.1"
val LogbackVer = "1.2.3"
val ScalaTestVer = "3.2.2"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVer
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
libraryDependencies += "com.typesafe" % "config" % ConfigVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % ScalaTestVer % "test"