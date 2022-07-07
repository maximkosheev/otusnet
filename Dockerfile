FROM bellsoft/liberica-openjdk-alpine:11

COPY target/otus-net-*.jar /otus-net.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/otus-net.jar"]
