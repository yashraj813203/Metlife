#!/bin/bash

echo "🐳 Building Claims Processing Platform Docker Image..."

# Build the Docker image
echo "Building Docker image..."
docker build -t claims-processing-platform:latest .

if [ $? -eq 0 ]; then
    echo "✅ Docker image built successfully!"
    echo ""
    echo "📦 Image Details:"
    docker images claims-processing-platform:latest
    echo ""
    echo "🚀 Next Steps:"
    echo "1. Test the image: docker run -p 8080:8080 claims-processing-platform:latest"
    echo "2. Push to registry: docker push your-registry/claims-processing-platform:latest"
    echo "3. Deploy: docker-compose up -d"
    echo ""
    echo "📊 Image Size: Check with 'docker images'"
else
    echo "❌ Docker build failed!"
    exit 1
fi