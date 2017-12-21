//please change these to reflect your project
name := "spark-scala-docker"
version := "0.0.0"

scalaVersion := "2.10.6"

//the provided tag tells assembly that this dep will be available on runtime jvm
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.6.0" % "provided"
)

//add your library dependencies here
libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-aws" % "2.7.3" excludeAll ExclusionRule(organization = "javax.servlet"),
  "org.postgresql" % "postgresql" % "9.4.1212"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

//don't run tests in parallel - breaks spark test harness
parallelExecution in Test := false

//don't run sbt test during assembly
test in assembly := {}

//which jar to use for sparkSubmit: the assembled fat jar
sparkSubmitJar := assembly.value.absolutePath

//variables set during sparkSubmit
sparkSubmitSparkArgs := Seq(
  "--master", sys.env.getOrElse("SPARK_MASTER_URL", "spark://spark-master:7077"),
  "--class", sys.env.getOrElse("SPARK_ENTRY_CLASS", "com.wink.spark.Run")
)

//mergeStrategy is used by assembly to resolve conflicting classpaths
// *NOTE* there may be differences in mergeStrategy for Spark 2.X and Spark 1.X, please keep that in mind
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
