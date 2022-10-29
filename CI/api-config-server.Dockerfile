FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/api-gateway/config-server/target/api-gateway*.jar /app/config-server.jar
EXPOSE 9149
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/config-server.jar"]