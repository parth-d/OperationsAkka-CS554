name := "OperationsAkka"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.17"
val ConfigVersion = "1.4.1"
val LogbackVer = "1.2.7"
val ScalaTestVer = "3.2.9"
val jacksonVer = "2.13.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVer
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
libraryDependencies += "com.typesafe" % "config" % ConfigVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % ScalaTestVer % "test"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVer
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % jacksonVer
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVer
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVer
libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % jacksonVer
libraryDependencies += "com.google.guava" % "guava" % "23.6-jre"
libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.toString()
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.toString()