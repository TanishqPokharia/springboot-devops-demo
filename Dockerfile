FROM eclipse-temurin:17-jdk

RUN apt-get update && \
    apt-get install -y maven curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
COPY mvnw.cmd .

RUN mvn dependency:go-offline -B

COPY src/ src/

RUN mvn clean package -DskipTests

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "target/spring-devops-demo-0.0.1-SNAPSHOT.jar"]