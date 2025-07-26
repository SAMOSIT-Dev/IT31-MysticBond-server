# ──────── Stage 1: Build ────────
FROM maven:3.9.6-openjdk-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ──────── Stage 2: Run ────────
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]