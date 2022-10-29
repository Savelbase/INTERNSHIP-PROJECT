FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/deposits/deposits-query-application/target/deposits*.jar /app/deposits-query-application.jar
EXPOSE 8102
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/deposits-query-application.jar"]