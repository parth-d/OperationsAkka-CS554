name := "OperationsAkka"

version := "0.1"

scalaVersion := "2.13.7"

val AkkaVersion = "2.6.17"
val ConfigVersion = "1.4.1"
val LogbackVer = "1.2.7"
val ScalaTestVer = "3.2.9"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVer
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
libraryDependencies += "com.typesafe" % "config" % ConfigVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % ScalaTestVer % "test"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.0"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.13.0"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.13.0"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.0"
libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.13.0"
libraryDependencies += "com.google.guava" % "guava" % "23.6-jre"
libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.13.6"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.6"