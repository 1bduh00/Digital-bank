# Use the official Maven image as the base image
FROM amazoncorretto:21

# the JAR file path
ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} app.jar

# Expose the port on which the Spring Boot application will run
EXPOSE 8085

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]