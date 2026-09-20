ARG REGISTRY=docker.m.daocloud.io/library

FROM ${REGISTRY}/gradle:8.10-jdk17 AS build
WORKDIR /app
COPY gradle-mirror.init.gradle /opt/gradle-mirror.init.gradle
COPY settings.gradle.kts build.gradle.kts ./
COPY src ./src
RUN --mount=type=cache,target=/cache/gradle \
    gradle -I /opt/gradle-mirror.init.gradle --gradle-user-home /cache/gradle \
      -Dorg.gradle.internal.http.connectionTimeout=60000 \
      -Dorg.gradle.internal.http.socketTimeout=120000 \
      shadowJar --no-daemon

FROM ${REGISTRY}/eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
