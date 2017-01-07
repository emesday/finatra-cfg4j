name := "finatra-cfg4j-app"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Twitter Maven" at "http://maven.twttr.com"
)

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % "2.5.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.cfg4j" % "cfg4j-core" % "4.4.0",
  "org.cfg4j" % "cfg4j-git" % "4.4.0"
)

