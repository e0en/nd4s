lazy val nd4jVersion = SettingKey[String]("nd4jVersion")

lazy val root = (project in file(".")).settings(
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.6", "2.11.8"),
  name := "nd4s",
  version := "0.7.2",
  organization := "org.nd4j",
  resolvers += "Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository",
  nd4jVersion := "0.7.2",
  libraryDependencies ++= Seq(
    "com.nativelibs4java" %% "scalaxy-loops" % "0.3.4",
    "org.nd4j" % "nd4j-api" % nd4jVersion.value,
    "org.nd4j" % "nd4j-native-platform" % nd4jVersion.value % Test,
    "org.scalatest" %% "scalatest" % "2.2.6" % Test,
    "ch.qos.logback" % "logback-classic" % "1.1.7" % Test,
    "org.scalacheck" %% "scalacheck" % "1.12.5" % Test,
    "org.scalanlp" %% "breeze" % "0.12" % Test,
    "com.github.julien-truffaut" %% "monocle-core" % "1.2.0" % Test
  ),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions", "-language:higherKinds", "-language:postfixOps"),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  publishTo <<= version {
    v =>
	  val ossLocal = "http://ec2-54-200-65-148.us-west-2.compute.amazonaws.com:8081/artifactory/"
      if (v.trim.endsWith("SNAPSHOT"))
		Some("snapshots" at ossLocal + "libs-snapshot-local")
      else
		Some("releases" at ossLocal + "libs-release-local")
  },
  pomExtra := {
    <url>http://nd4j.org/</url>
      <licenses>
        <license>
          <name>Apache License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <connection>scm:git@github.com:SkymindIO/deeplearning4j.git</connection>
        <developerConnection>scm:git:git@github.com:SkymindIO/deeplearning4j.git</developerConnection>
        <url>git@github.com:deeplearning4j/deeplearning4j.git</url>
        <tag>HEAD</tag>
      </scm>
      <developers>
        <developer>
          <id>agibsonccc</id>
          <name>Adam Gibson</name>
          <email>adam@skymind.io</email>
        </developer>
        <developer>
          <id>taisukeoe</id>
          <name>Taisuke Oe</name>
          <email>oeuia.t@gmail.com</email>
        </developer>
      </developers>
  },
  credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
  releasePublishArtifactsAction := com.typesafe.sbt.pgp.PgpKeys.publishSigned.value,
  releaseCrossBuild := true,
  initialCommands in console := "import org.nd4j.linalg.factory.Nd4j; import org.nd4s.Implicits._")
