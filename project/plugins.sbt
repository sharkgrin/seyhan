// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % System.getProperty("play.version"))

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.2.5")

libraryDependencies += "org.javassist" % "javassist" % "3.18.2-GA"