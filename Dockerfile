# 1. JDK 21 Alpine 사용 (Gradle 빌드도 JDK 21 사용)
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Gradle 캐시 최적화
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 프로젝트 코드 복사 및 빌드 실행
COPY . ./
RUN ./gradlew bootJar --no-daemon

# 2. 실행용 JDK 21 설정
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 실행 시 환경변수 설정
ENV PORT=8080

# Spring Boot 실행
CMD ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]
