ARG REGISTRY=docker.m.daocloud.io/library
FROM ${REGISTRY}/gradle:8.10-jdk21 AS build
WORKDIR /app
COPY settings.gradle.kts build.gradle.kts ./
COPY src ./src
RUN gradle shadowJar --no-daemon

FROM ${REGISTRY}/eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
