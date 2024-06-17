FROM maven:3 AS builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:20
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 7070
ENTRYPOINT ["java", "-jar", "/app/*.jar"]