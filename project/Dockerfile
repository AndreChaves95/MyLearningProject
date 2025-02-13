FROM openjdk:latest
VOLUME /tmp
COPY src/main/resources/db/changelogs/liquibase-changelog.yml /liquibase
COPY target/project-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]