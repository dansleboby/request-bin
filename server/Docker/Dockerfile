FROM maven:3.6.2-jdk-11-slim AS build
COPY pom.xml /usr/src/app
COPY src /usr/src/app/src
RUN mvn dependency:go-offline -f /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml -T 1C package

FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/requestbin-0.0.1-SNAPSHOT.jar /usr/app/requestbin-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/usr/app/requestbin-0.0.1-SNAPSHOT.jar"]