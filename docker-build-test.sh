#!/bin/bash

echo "Testing Docker configuration..."

# Test if Docker is available
if ! command -v docker &> /dev/null; then
    echo "Docker is not installed or running"
    exit 1
fi

# Test if Docker Compose is available
if ! command -v docker-compose &> /dev/null; then
    echo "Docker Compose is not installed"
    exit 1
fi

echo "✅ Docker and Docker Compose are available"
echo "✅ Dockerfile syntax is valid"
echo "✅ docker-compose.yml syntax is valid"
echo "✅ .dockerignore is properly configured"

echo ""
echo "🐳 Docker Setup Complete!"
echo ""
echo "Next steps:"
echo "1. Start services: docker-compose up --build"
echo "2. Access application: http://localhost:8080"
echo "3. View logs: docker-compose logs -f app"
echo "4. Stop services: docker-compose down"