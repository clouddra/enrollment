FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app
COPY . /workspace/app
RUN ./gradlew clean build

FROM build as deployable
ENTRYPOINT ["java","-jar", "build/libs/enrollment-0.0.1-SNAPSHOT.jar"]