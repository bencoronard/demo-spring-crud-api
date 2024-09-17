FROM gradle:8.10.1-jdk22 AS build

WORKDIR /app

COPY build.gradle settings.gradle

COPY src ./src

RUN gradle build --no-daemon

FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S boot -G spring

USER boot:spring

COPY --from=build /app/build/libs*.jar app.jar

RUN chown -R boot:spring /app

USER boot:spring

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "app.jar"]
