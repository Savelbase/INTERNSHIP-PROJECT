FROM maven:3.8.5-openjdk-18-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/bank-client/bank-client-application/target/bank-client*.jar /app/bank-client-application.jar
EXPOSE 8094
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/bank-client-application.jar"]