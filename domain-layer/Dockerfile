# Build stage
FROM gradle:8.14-jdk17 AS build
WORKDIR /app

# Copy build files
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY proto ./proto

# Copy both project build files (needed for multi-project build)
COPY data-layer/build.gradle ./data-layer/
COPY domain-layer/build.gradle ./domain-layer/

# Copy domain-layer source
COPY domain-layer/src ./domain-layer/src

# Build the application
RUN gradle :domain-layer:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/domain-layer/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

