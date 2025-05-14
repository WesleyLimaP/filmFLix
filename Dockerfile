FROM eclipse-temurin:21-jdk-alpine
WORKDIR app/
COPY ./target/project_filmFlix-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]