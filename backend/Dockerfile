FROM openjdk:17
WORKDIR /app
RUN mvn clean package
COPY ./target/GoogleDriveClone-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "GoogleDriveClone-0.0.1-SNAPSHOT.jar"]