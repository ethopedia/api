FROM openjdk:8-jre-alpine

COPY . /app
WORKDIR /app

EXPOSE 7250

ENTRYPOINT ["java", "-jar", "build/libs/ethopedia-1.0-SNAPSHOT-all.jar", "-parameters"]
