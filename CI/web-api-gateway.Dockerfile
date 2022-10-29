FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/api-gateway/web-api-gateway/target/api-gateway*.jar /app/web-api-gateway.jar
EXPOSE 9769
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/web-api-gateway.jar"]