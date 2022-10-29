FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/bank-info/bank-info-application/target/bank-info*.jar /app/bank-info-application.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/bank-info-application.jar"]