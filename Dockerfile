FROM alpine:3

WORKDIR /app

COPY target/EnviroFund-1.0-SNAPSHOT.jar.original enviro.jar

RUN apk add --no-cache openjdk11 maven

CMD ["java", "-jar", "enviro.jar"]