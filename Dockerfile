# Stage 1: Build the application
FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/dev-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "dev-0.0.1-SNAPSHOT.jar"]