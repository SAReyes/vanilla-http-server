FROM openjdk:8-jdk-alpine AS builder
COPY . /opt/store
WORKDIR /opt/store
RUN ./mvnw clean package -DskipTests

FROM openjdk:8-jre-alpine
COPY --from=builder /opt/store/store-example/target/*-dependencies.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
