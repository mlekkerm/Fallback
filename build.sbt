name := "Fallback"

version := "0.1"

scalaVersion := "2.11.11"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-http_2.11
libraryDependencies += "com.typesafe.akka" % "akka-http_2.11" % "10.0.10"
// https://mvnrepository.com/artifact/com.typesafe.akka/akka-stream_2.11
libraryDependencies += "com.typesafe.akka" % "akka-stream_2.11" % "2.4.19"
// https://mvnrepository.com/artifact/com.typesafe.akka/akka-actor_2.11
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.19"


// https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "5.4.2"
// https://mvnrepository.com/artifact/com.sksamuel.elastic4s/elastic4s-http_2.11
libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-http_2.11" % "6.0.0-M4"
