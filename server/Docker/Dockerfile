FROM maven:3.6.2-jdk-11-slim AS dependencies
COPY pom.xml /usr/src/app
RUN mvn dependency:go-offline -f /usr/src/app/pom.xml
COPY src /usr/src/app/src
RUN mvn -f /usr/src/app/pom.xml -T 1C package

FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/requestbin-0.0.1-SNAPSHOT.jar /usr/app/requestbin-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/usr/app/requestbin-0.0.1-SNAPSHOT.jar"]