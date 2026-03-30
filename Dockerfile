# Build stage using official Maven Image
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Render uses the PORT environment variable
ENV PORT=8081
EXPOSE ${PORT}

# Run the application
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
