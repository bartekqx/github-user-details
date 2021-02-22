FROM openjdk11-alpine
ARG JAR_FILE=*-exec.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]