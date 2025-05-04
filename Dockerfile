from eclipse-temurin:21-jdk-alpine
workdir /app
copy ./target/project_filmFlix-0.0.1-SNAPSHOT.jar app.jar
cmd ["java", "-jar", "app.jar"]