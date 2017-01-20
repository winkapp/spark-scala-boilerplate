package com.wink.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Use this class as the entry to your application code
  * Feel free to change this any way you like - it just serves as a convenient entry for SBT and Spark
  * IF your job needs more than one entry point, you probably need more than one job
  */
object Run extends App {
  val conf = new SparkConf().setAppName("Your App Name Here")
  val sc = new SparkContext(conf)

  val a = sc.parallelize(1 to Int.MaxValue)

  println(a.count())
  println(a.map(x => x + 1).filter(x => x % 21 == 0).take(150).toList)

  sc.stop
}
