FROM maven:latest
RUN mkdir -p /app/src
COPY ./src /app/src
COPY ./pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean package
RUN mkdir -p /app/target/csv
COPY ./csv /app/target/csv
WORKDIR /app/target
#CMD ["java","-jar","mqttprovider.jar"]
