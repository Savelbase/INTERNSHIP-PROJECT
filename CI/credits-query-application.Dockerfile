FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/credits/credits-query-application/target/credits*.jar /app/credits-query-application.jar
EXPOSE 8098
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/credits-query-application.jar"]