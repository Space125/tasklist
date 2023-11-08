FROM gradle:8.2.1-jdk AS build
WORKDIR /workdir
COPY  . /workdir
RUN ./gradlew bootJar

FROM openjdk:17-jdk-slim
COPY --from=build /workdir/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
