FROM openjdk:11-jdk

WORKDIR /root
MAINTAINER rebwon
EXPOSE 8080
RUN mvn clean build

FROM openjdk:11-jre

WORKDIR /root
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]