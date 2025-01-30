# 1. Build Stage (Gradle 빌드)
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Gradle 캐시 최적화: Gradle Wrapper 및 의존성 캐싱
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 프로젝트 코드 복사 및 빌드 실행
COPY . ./
RUN ./gradlew bootJar --no-daemon

# 2. Run Stage (최종 실행 환경)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 환경 변수로 포트 설정 (Railway 호환)
ENV PORT=8080

# 실행 명령어 (PORT 환경 변수 적용)
CMD ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]
