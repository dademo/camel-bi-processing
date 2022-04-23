FROM docker.io/library/openjdk:11-jre-slim

RUN useradd -m -c 'Java runner user' -d /app java
USER java:java

## ARGS
ARG JAR_FILE=target/dependency/*.jar

## BUILDING
COPY --chown=java:java "${JAR_FILE}" /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
