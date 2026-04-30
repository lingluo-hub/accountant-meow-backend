.PHONY: help build run test clean docker-build docker-up docker-down docker-logs migrate migrate-down

# Variables
APP_NAME := accountant-meow-backend
GO := go
GOFLAGS := -v

.DEFAULT_GOAL := help

## help: Display this help message
help:
	@grep -E '^## ' Makefile | cut -d' ' -f2-

## build: Build the application
build:
	@echo "Building $(APP_NAME)..."
	@$(GO) build $(GOFLAGS) -o bin/$(APP_NAME) ./cmd/main.go

## run: Run the application
run:
	@echo "Running $(APP_NAME)..."
	@$(GO) run ./cmd/main.go

## test: Run tests
test:
	@echo "Running tests..."
	@$(GO) test -v ./...

## test-coverage: Run tests with coverage report
test-coverage:
	@echo "Running tests with coverage..."
	@$(GO) test -v -coverprofile=coverage.out ./...
	@$(GO) tool cover -html=coverage.out -o coverage.html
	@echo "Coverage report generated: coverage.html"

## clean: Clean build artifacts
clean:
	@echo "Cleaning..."
	@rm -rf bin/
	@$(GO) clean

## fmt: Format code
fmt:
	@echo "Formatting code..."
	@$(GO) fmt ./...

## lint: Run linter
lint:
	@echo "Running linter..."
	@golangci-lint run ./...

## docker-build: Build Docker image
docker-build:
	@echo "Building Docker image..."
	@docker build -t $(APP_NAME):latest .

## docker-up: Start Docker containers
docker-up:
	@echo "Starting Docker containers..."
	@docker-compose up -d
	@echo "Containers started. Access the app at http://localhost:8080"
	@echo "Swagger docs at http://localhost:8080/swagger/index.html"

## docker-down: Stop Docker containers
docker-down:
	@echo "Stopping Docker containers..."
	@docker-compose down

## docker-logs: View Docker logs
docker-logs:
	@docker-compose logs -f app

## docker-clean: Remove Docker containers and volumes
docker-clean:
	@echo "Removing Docker containers and volumes..."
	@docker-compose down -v

## migrate: Run database migrations
migrate:
	@echo "Running migrations..."
	@migrate -path migrations -database "$(DB_URL)" up

## migrate-down: Rollback database migrations
migrate-down:
	@echo "Rolling back migrations..."
	@migrate -path migrations -database "$(DB_URL)" down

## deps: Download dependencies
deps:
	@echo "Downloading dependencies..."
	@$(GO) mod download
	@$(GO) mod tidy

## swagger: Generate Swagger documentation
swagger:
	@echo "Generating Swagger documentation..."
	@swag init -g cmd/main.go

## dev: Run in development mode with hot reload (requires air)
dev:
	@echo "Running in development mode..."
	@air
