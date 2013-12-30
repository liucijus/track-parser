name := "track-parser"

version := "1.0"

sbtBinaryVersion := "0.13"

autoScalaLibrary := false

javacOptions ++= Seq("-source", "1.7")

libraryDependencies += "com.google.guava" % "guava" % "15.0"

libraryDependencies += "joda-time" % "joda-time" % "2.3"

//libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.hamcrest" % "hamcrest-all" % "1.3"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % "test"
