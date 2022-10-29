FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/src/rmt-mobile-new-backend
COPY . .
RUN mvn package

FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/src/rmt-mobile-new-backend/media-storage/media-storage-command-application/target/media-storage*.jar /app/media-storage-command-application.jar
EXPOSE 8095
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/media-storage-command-application.jar"]