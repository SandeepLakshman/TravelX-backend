# Build stage using Maven Wrapper
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app

# Copy the pom.xml and maven wrapper first to take advantage of Docker caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
# Download dependencies (this will be cached)
RUN ./mvnw dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests

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
