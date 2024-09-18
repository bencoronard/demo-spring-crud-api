FROM gradle:8.10.1-jdk22 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S boot -G spring

COPY --from=build --chown=boot:spring /app/build/libs/api_gateway_demo-0.0.1-SNAPSHOT.jar app.jar

USER boot:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

