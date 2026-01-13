# -------- BUILD STAGE --------
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copy Gradle wrapper and config first (better caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Download dependencies
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build Spring Boot jar
RUN ./gradlew bootJar --no-daemon


# -------- RUNTIME STAGE --------
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy only the built jar
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]