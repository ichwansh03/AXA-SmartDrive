FROM openjdk:17 AS build

WORKDIR /app

COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -Dmaven.test.skip

WORKDIR /discovery

COPY --from=build /app/target/*.jar /discovery/discovery-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","discovery-server-0.0.1-SNAPSHOT.jar"]