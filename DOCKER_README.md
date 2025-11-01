# Docker Setup for Claims Processing Platform

## Quick Start

### Prerequisites
- Docker and Docker Compose installed
- Java 17+ (for local development)

### Running the Application

#### Option 1: Using Docker Compose (Recommended)
```bash
# Start all services (MongoDB + Application)
docker-compose up --build

# Run in detached mode
docker-compose up -d --build

# View logs
docker-compose logs -f app

# Stop all services
docker-compose down
```

#### Option 2: Using Dockerfile only
```bash
# Build the Docker image
docker build -t claims-processing-platform .

# Run the container
docker run -p 8080:8080 --name claims-app claims-processing-platform
```

## Services

### MongoDB
- **Image**: mongo:7.0
- **Port**: 27017
- **Database**: claimsdb
- **Credentials**: admin/password
- **Data Volume**: mongodb_data

### Application
- **Base Image**: openjdk:17-jdk-slim
- **Port**: 8080
- **Health Check**: Every 30 seconds
- **Environment Variables**: MongoDB connection details

## Environment Variables

| Variable | Description | Default |
|-----------|-------------|---------|
| `SPRING_DATA_MONGODB_HOST` | MongoDB host | mongodb |
| `SPRING_DATA_MONGODB_PORT` | MongoDB port | 27017 |
| `SPRING_DATA_MONGODB_DATABASE` | Database name | claimsdb |
| `SPRING_DATA_MONGODB_USERNAME` | MongoDB username | admin |
| `SPRING_DATA_MONGODB_PASSWORD` | MongoDB password | password |

## API Endpoints

Once running, access the application at:
- **Health Check**: http://localhost:8080/
- **Create Policy**: POST http://localhost:8080/api/v1/policies/createPolicy
- **Get All Claims**: GET http://localhost:8080/api/v1/policies/getAllClaims
- **Get Claim by ID**: GET http://localhost:8080/api/v1/policies/getClaimsById/{id}
- **Claim Summary**: GET http://localhost:8080/api/v1/policies/summary/status

## Development Workflow

1. **Make changes to code**
2. **Rebuild and restart**:
   ```bash
   docker-compose up --build --force-recreate
   ```

3. **View logs**:
   ```bash
   docker-compose logs -f app
   ```

## Production Considerations

### Security
- Change default MongoDB credentials in production
- Use environment-specific configuration files
- Enable HTTPS/SSL termination

### Performance
- Use multi-stage builds for smaller images
- Configure appropriate JVM heap sizes
- Enable health checks for load balancers

### Monitoring
- Configure external logging (ELK stack, etc.)
- Set up application monitoring (Prometheus, etc.)
- Use centralized configuration management

## Troubleshooting

### Port Conflicts
If port 8080 is already in use:
```bash
# Change port in docker-compose.yml
ports:
  - "8081:8080"  # Use 8081 instead
```

### Database Connection Issues
```bash
# Check MongoDB container status
docker-compose ps mongodb

# View MongoDB logs
docker-compose logs mongodb

# Connect to MongoDB
docker exec -it claims-mongodb mongosh -u admin -p password claimsdb
```

### Build Failures
```bash
# Clean build
docker-compose down
docker system prune -f
docker-compose up --build

# View build logs
docker-compose logs app
```

## Docker Commands Reference

```bash
# Build image only
docker build -t claims-processing-platform .

# Run with custom environment
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  --name claims-app \
  claims-processing-platform

# Execute commands in container
docker exec -it claims-app bash

# Access MongoDB directly
docker exec -it claims-mongodb mongosh -u admin -p password claimsdb
```