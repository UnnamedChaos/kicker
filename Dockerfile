FROM openjdk:8-jdk-alpine
MAINTAINER Snieder

ARG NAME=kicker
ARG	BUILD_DATE=none 
ARG	VCS_REF=none
ARG	ENVIRONMENT=default

VOLUME /tmp
ADD backend/target/kick-backend-*.jar /app/kick-backend.jar
WORKDIR /app

EXPOSE 8080

ENV JAVA_OPTS="-Xmx2048m -XX:-UseGCOverheadLimit"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/kick-backend.jar"]