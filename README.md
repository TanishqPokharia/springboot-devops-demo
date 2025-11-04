# Spring Boot DevOps Demo

A simple Spring Boot application with Maven for DevOps practice. This application provides REST endpoints for system health checks and product management.

## Features

- **Health Check Endpoints**: System health monitoring
- **Product Management**: CRUD operations for products
- **Spring Boot Actuator**: Built-in monitoring and health checks
- **Maven Build**: Easy build and dependency management

## Endpoints

### Health Check
- `GET /` - Welcome message and endpoint listing
- `GET /health` - Custom health check endpoint
- `GET /actuator/health` - Spring Boot Actuator health endpoint

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/stats` - Get product statistics

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+ (optional if using Maven wrapper)

### Running the Application

#### Option 1: Using Maven Wrapper (Recommended)

The Maven wrapper (`mvnw`) is included and ensures consistent Maven version across environments.

1. **Navigate to the project directory:**
   ```bash
   cd /home/tanishqpokharia/Devops/spring-devops-demo
   ```

2. **Build the application:**
   ```bash
   ./mvnw clean compile
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Package and run as JAR:**
   ```bash
   ./mvnw clean package
   java -jar target/spring-devops-demo-0.0.1-SNAPSHOT.jar
   ```

#### Option 2: Using System Maven

If you have Maven installed globally:

1. **Build the application:**
   ```bash
   mvn clean compile
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Package and run as JAR:**
   ```bash
   mvn clean package
   java -jar target/spring-devops-demo-0.0.1-SNAPSHOT.jar
   ```

### Testing Endpoints

Once the application is running (default port 8080), test the endpoints:

```bash
# Health checks
curl http://localhost:8080/
curl http://localhost:8080/health
curl http://localhost:8080/actuator/health

# Product endpoints
curl http://localhost:8080/api/products
curl http://localhost:8080/api/products/1
curl http://localhost:8080/api/products/category/Electronics
curl http://localhost:8080/api/products/stats
```

## Build Commands

### Using Maven Wrapper (mvnw)
```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package the application
./mvnw clean package

# Run Spring Boot application directly
./mvnw spring-boot:run

# Run the packaged JAR
java -jar target/spring-devops-demo-0.0.1-SNAPSHOT.jar
```

### Using System Maven (mvn)
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package the application
mvn clean package

# Run Spring Boot application directly
mvn spring-boot:run

# Run the packaged JAR
java -jar target/spring-devops-demo-0.0.1-SNAPSHOT.jar
```

## Configuration

The application can be configured via `src/main/resources/application.properties`:

- Server port: `server.port=8080`
- Actuator endpoints: `management.endpoints.web.exposure.include=health,info`
- Logging level: `logging.level.com.devops.demo=INFO`

## Docker Ready

This application is ready for containerization. You can create a Dockerfile to build and deploy it in containers for your DevOps pipeline.

## DevOps Integration

Perfect for practicing:
- CI/CD pipelines
- Docker containerization
- Kubernetes deployment
- Health monitoring
- Load balancing
- API testing