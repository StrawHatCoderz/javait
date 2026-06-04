FROM gradle:jdk25 AS builder

WORKDIR /app

COPY . .

RUN gradle clean build

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8000

CMD ["java", "-jar", "app.jar"]