FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY target/*.jar app.jar

CMD ["sh", "-c", "java -jar -Dserver.port=${PORT:-8080} app.jar"]