FROM maven:3.8.5-jdk-11 as build

COPY pom.xml .
COPY src src

RUN mvn -B package --file pom.xml -DskipTests

FROM openjdk:11-jdk
COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
