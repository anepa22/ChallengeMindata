FROM openjdk:11.0.4-jre-slim
MAINTAINER anepanet.com.ar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} challenge-mindata-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/challenge-mindata-1.0.0-SNAPSHOT.jar"]