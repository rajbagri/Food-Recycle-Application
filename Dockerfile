# Use lightweight Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built JAR into container
COPY target/restApi-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
