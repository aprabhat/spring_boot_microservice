# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built Spring Boot JAR file into the container
# Assuming the Gradle build output is located in 'build/libs/'
COPY build/libs/inventory-service.jar /app/inventory-service.jar

# Expose the port that your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# Set the command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]