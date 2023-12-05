#
# Build stage
#
FROM maven:latest AS build
RUN mkdir -p /app/src/main
COPY ./src/main /app/src/main
COPY ./pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean package -P production -D vaadin.force.production.build=true
#
# Package stage
#
FROM openjdk
COPY --from=build /app /app
WORKDIR /app/target
ENTRYPOINT ["java","-jar","mqttprovider.jar"]