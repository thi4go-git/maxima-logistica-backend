FROM maven:3.8.6-jdk-11 as build
WORKDIR /build
COPY . .
RUN mvn clean package -P production -DskipTests


FROM openjdk:11
WORKDIR /app
COPY --from=build ./build/target/*.jar ./application.jar
EXPOSE 8080

ENTRYPOINT java -jar application.jar