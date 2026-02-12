FROM amazoncorretto:17 AS builder

WORKDIR /build
COPY . .
RUN chmod +x ./gradlew && ./gradlew clean bootJar --no-daemon

FROM amazoncorretto:17-alpine-jdk

WORKDIR /app
COPY --from=builder /build/build/libs/*.jar /app/app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
