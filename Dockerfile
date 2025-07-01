# v1 (~250mb)
# Multi-stage build for minimal image size
# Build stage (JDK required for compilation)
FROM bellsoft/liberica-runtime-container:jdk-17-musl AS builder
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage (Minimal JRE image)
FROM bellsoft/liberica-runtime-container:jre-17-musl
WORKDIR /app
COPY --from=builder /app/target/ecommerce-app-0.0.1-SNAPSHOT.jar ecommerce-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ecommerce-app-0.0.1-SNAPSHOT.jar"]



# # v2 (~450mb)
# # ---------- Stage 1: Build the app ----------
# FROM maven:3.9.6-eclipse-temurin-17 AS builder
#
# # Set working directory
# WORKDIR /app
#
# # Copy the whole project
# COPY . .
#
# # Build the application without tests
# RUN ./mvnw clean package -DskipTests
#
# # ---------- Stage 2: Run with minimal image ----------
# FROM gcr.io/distroless/java17
#
# # Copy only the jar from the build stage
# COPY --from=builder /app/target/ecommerce-app-0.0.1-SNAPSHOT.jar app.jar
#
# # Start the Spring Boot app
# ENTRYPOINT ["app.jar"]

