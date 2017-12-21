package com.wink.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.scalatest.{BeforeAndAfterAll, Suite}

/**
  * test harness that allows access to a spark context within tests
  * Have your test spec extend SparkSessionSpec to gain access to spark - a local SparkSession
  * and testImplicits - an importable set of implicits for Dataset/Dataframe transformations
  *
  */

trait SparkSessionSpec extends BeforeAndAfterAll {
  this: Suite =>

  private var _spark: SparkContext = _

  override def beforeAll(): Unit = {
    super.beforeAll()

    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)

    _spark = SparkContext.getOrCreate(conf)

  }

  override def afterAll(): Unit = {
    if (_spark != null) {
      _spark.stop
      _spark = null
    }
    super.afterAll()
  }

  def spark: SparkContext = _spark

}
