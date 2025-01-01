FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Шаг 2: Запуск
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/GoogleDriveClone-0.0.1-SNAPSHOT.jar /app/GoogleDriveClone-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/GoogleDriveClone-0.0.1-SNAPSHOT.jar"]