#
# Build stage
#
FROM maven:latest AS build
RUN mkdir -p /app/src
COPY ./src /app/src
COPY ./pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean package
#
# Package stage
#
FROM openjdk:14
COPY --from=build /app /app
RUN mkdir -p /app/target/csv
COPY ./csv /app/target/csv
COPY ./sumoFiles /app/target/sumoFiles
WORKDIR /app/target
ENTRYPOINT ["java","-jar","mqttprovider.jar"]
