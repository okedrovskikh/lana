FROM gradle:8.2-jdk17 as build
COPY ./ /usr/lana
WORKDIR /usr/lana
RUN gradle build -x test

FROM openjdk:17
COPY --from=build /usr/lana/build/libs/*.jar /usr/app/App.jar
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "App.jar"]
