FROM bellsoft/liberica-openjdk-alpine:11

ARG JAR_VERSION=0.0.1-SNAPSHOT
ARG JAR_FILE=build/libs/otuset-${JAR_VERSION}.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080