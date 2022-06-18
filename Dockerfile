FROM openjdk:8-jre
RUN mkdir -p app/csv
COPY ./target /app
COPY ./csv /app/csv
WORKDIR /app
ENTRYPOINT java -jar mqttprovider.jar
