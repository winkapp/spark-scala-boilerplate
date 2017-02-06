package com.wink.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SQLContext, SQLImplicits, SparkSession}
import org.scalatest.{BeforeAndAfterAll, Suite}

/**
  * test harness that allows access to a spark context within tests
  * Have your test spec extend SparkSessionSpec to gain access to spark - a local SparkSession
  * and testImplicits - an importable set of implicits for Dataset/Dataframe transformations
  *
  */

trait SparkSessionSpec extends BeforeAndAfterAll {
  this: Suite =>

  private var _spark: SparkSession = _

  protected object testImplicits extends SQLImplicits {
    protected override def _sqlContext: SQLContext = _spark.sqlContext
  }

  override def beforeAll(): Unit = {
    super.beforeAll()

    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)

    _spark = SparkSession.builder().config(conf).getOrCreate()
  }

  override def afterAll(): Unit = {
    if (_spark != null) {
      _spark.stop
      _spark = null
    }
    super.afterAll()
  }

  def spark: SparkSession = _spark

}
