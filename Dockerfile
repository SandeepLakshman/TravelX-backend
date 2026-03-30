# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/travel-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
# Use the PORT environment variable if provided by Render, else default to 8081
ENTRYPOINT ["java", "-Dserver.port=${PORT:8081}", "-jar", "app.jar"]
