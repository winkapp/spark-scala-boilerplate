#FROM winkapp/spark-standalone:2.1.0
FROM openjdk:8

# Install tools
RUN apt-get update && apt-get install -y mysql-client postgresql-client curl wget unzip

#install sbt
ENV SBT_VERSION 0.13.13
ENV SCALA_VERSION 2.11.7  
ENV SBT_OPTS -Xmx2G -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -Xss2M -Duser.timezone=GMT  

RUN wget https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb  
RUN dpkg -i sbt-$SBT_VERSION.deb  
# install scala  
RUN wget https://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.deb  
RUN dpkg -i scala-$SCALA_VERSION.deb  
# fetch base dependencies  
RUN sbt compile  

RUN mkdir /root/spark_job
WORKDIR /root/spark_job

# Add sbt config
COPY project ./project
COPY build.sbt ./build.sbt

# Add src
COPY src ./src

#Run tests
ONBUILD RUN sbt test

#set entrypoint to use sbt sparkSubmit plugin
#ENTRYPOINT ["/bin/bash"]
#CMD ["sbt", "sparkSubmit"]
