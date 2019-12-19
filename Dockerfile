FROM maven:3.5-jdk-8

VOLUME /tmp

COPY . /app
COPY ./pom.xml /app