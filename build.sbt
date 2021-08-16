name := "BankingSystem_Scala_Akka"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.6.15"

val AkkaVersion = "2.6.15"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test