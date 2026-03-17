# Stage 1: Build the JAR using Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY product-service/pom.xml product-service/pom.xml
COPY order-service/pom.xml order-service/pom.xml
RUN mvn dependency:go-offline -pl api-gateway -am -q
COPY api-gateway/src api-gateway/src
RUN mvn clean package -pl api-gateway -am -DskipTests

# Stage 2: Run the JAR on a lightweight JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/api-gateway/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
