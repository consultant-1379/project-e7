# # Build stage
# FROM maven:3.6.3-openjdk-17-slim AS build
# COPY src /home/app/src
# COPY pom.xml /home/app
# RUN mvn -f /home/app/pom.xml clean install -DskipTests

# # Package stage
# FROM openjdk:17-jdk-alpine
# COPY --from=build /home/app/target/*.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","app.jar"]

FROM mediasol/openjdk17-slim-jprofiler
ENV PORT 8080
COPY target/*.jar /opt/app.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar