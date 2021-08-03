FROM openjdk:11
LABEL maintainer="hainv"
ARG version=0.0.1

ADD target/redis-sentinel-${version}.jar /redis-sentinel.jar
ENTRYPOINT ["java", "-jar", "/redis-sentinel.jar"]
