FROM openjdk:17-jdk-alpine
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=dev

ARG JAR_FILE=app.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]