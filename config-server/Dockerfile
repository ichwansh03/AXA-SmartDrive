FROM openjdk:17 AS build

WORKDIR /app

COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -Dmaven.test.skip

WORKDIR /configserver

COPY --from=build /app/target/*.jar /configserver/config-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","config-server-0.0.1-SNAPSHOT.jar"]