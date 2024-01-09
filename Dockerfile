FROM openjdk:17-jdk-slim

ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILE}", "-jar","/app.jar"]