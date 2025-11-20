FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests clean package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV DB_HOST=db \
    DB_NAME=codebankers_db \
    DB_USERNAME=app \
    DB_PASSWORD=app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
