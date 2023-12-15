FROM openjdk:17 AS build

WORKDIR /app

COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -Dmaven.test.skip

WORKDIR /masterservice

COPY --from=build /app/target/*.jar /masterservice/report-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","master-service-0.0.1-SNAPSHOT.jar"]