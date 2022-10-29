FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/authorization/authorization-application/target/authorization*.jar /app/authorization.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/authorization.jar"]