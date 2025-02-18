FROM maven:3.8.7-jdk-8 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM openjdk:8-jre-alpine