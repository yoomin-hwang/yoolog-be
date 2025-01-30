FROM openjdk:17-jdk-slim

WORKDIR /app

COPY deploy/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]