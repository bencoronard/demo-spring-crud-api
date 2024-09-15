# Use the official Gradle image to create a build artifact.
FROM gradle:7.4.2-jdk17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle configuration files
COPY build.gradle settings.gradle ./

# Copy the source code
COPY src ./src

# Build the application
RUN gradle build --no-daemon

# Use OpenJDK for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Create a new user 'appuser' with user ID 1000
RUN adduser --system --group --uid 1000 appuser

# Copy the built artifact from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Change ownership of the app files to the new user
RUN chown -R appuser:appuser /app

# Use the new 'appuser' to run the application
USER appuser

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]