# 1. JDK 21 Alpine 사용
FROM eclipse-temurin:21-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 프로젝트 코드 복사
COPY . ./

# 4. Gradle을 사용하여 빌드
RUN ./gradlew bootJar --no-daemon

# 5. 실행 가능한 JAR 파일 복사
COPY --from=0 /app/build/libs/*.jar app.jar

# 6. 환경변수로 포트 설정 (Koyeb 호환)
ENV PORT=8080

# 7. 애플리케이션 실행
CMD ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]
