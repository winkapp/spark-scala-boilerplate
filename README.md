# spark-scala-docker
Containerized Spark job optimized for scala - just drop your code in the src directory and run it

*TODO* use prebuilt jars rather than rebuilding the fat jars all the time

## Getting Started
1. To get started, fork this boilerplate project: https://github.com/winkapp/spark-scala-boilerplate
1. Drop code in the `src/main/scala` folder
1. Run `docker-compose run spark-job sbt sparkSubmit`

## Testing
1. This project uses [scalatest](http://www.scalatest.org/)
1. Drop your tests in `src/test/scala` folder
1. To run the tests: `docker-compose run spark-job sbt test`

## Dev
1. Spin up local dockerized cluster with `docker-compose -f ./docker-compose-spark_cluster.yml up`
1. Compile and run your job against it with `docker-compose -f ./docker-compose-spark_cluster.yml -f ./docker-compose.yml run spark-job sbt sparkSubmit`
