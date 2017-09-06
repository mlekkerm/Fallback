name := "Fallback"

version := "0.1"

scalaVersion := "2.11.11"

val akkaVersion = "2.5.4"
val akkaHttpVersion = "10.0.10"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor"  % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion
