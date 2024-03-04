FROM maven AS build
RUN mkdir -p /Backend
WORKDIR /Backend
COPY pom.xml /Backend
COPY src /Backend/src
RUN mvn -f pom.xml clean
RUN mvn install -DskipTests

FROM openjdk:12
COPY --from=build /Backend/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
