FROM arm64v8/openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ./target/financialSystem.jar ./financialSystem.jar
ENTRYPOINT ["java", "-jar", "financialSystem.jar"]