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
