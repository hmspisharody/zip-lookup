FROM openjdk:8-alpine
WORKDIR /app
COPY /target/*.jar zip-lookup.jar
ENTRYPOINT ["java", "-jar", "zip-lookup.jar"]
