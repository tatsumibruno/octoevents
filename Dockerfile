FROM openjdk:11-slim-stretch

ADD /target/octoevents-api-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR app

EXPOSE 8080

ENTRYPOINT exec java -jar app.jar