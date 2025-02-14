FROM eclipse-temurin:21-jdk
VOLUME /tmp
COPY src/main/resources/db/changelogs/liquibase-changelog.yml /liquibase/db/changelogs/liquibase-changelog.yml
COPY target/project-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]