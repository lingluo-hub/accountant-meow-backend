package main

import (
	"fmt"
	"log"
	"os"

	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
	"github.com/swaggo/files"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"

	"github.com/lingluo-hub/accountant-meow-backend/docs"
	"github.com/lingluo-hub/accountant-meow-backend/internal/config"
	"github.com/lingluo-hub/accountant-meow-backend/internal/database"
	"github.com/lingluo-hub/accountant-meow-backend/internal/router"
)

// @title           Accountant Meow Backend API
// @version         2.0.0
// @description     记账喵后端 API 文档
// @termsOfService  http://swagger.io/terms/

// @contact.name   Support
// @contact.url    https://github.com/lingluo-hub/accountant-meow-backend

// @license.name  MIT
// @license.url   https://opensource.org/licenses/MIT

// @host      localhost:8080
// @BasePath  /api/v1

// @securityDefinitions.basic BasicAuth

func init() {
	if err := godotenv.Load(".env"); err != nil {
		log.Println("No .env file found, using environment variables")
	}
}

func main() {
	// Load configuration
	cfg := config.LoadConfig()

	// Initialize database
	db, err := database.InitDB(cfg)
	if err != nil {
		log.Fatalf("Failed to connect to database: %v", err)
	}

	// Initialize Redis
	redisClient, err := database.InitRedis(cfg)
	if err != nil {
		log.Fatalf("Failed to connect to Redis: %v", err)
	}

	// Set Gin mode
	if os.Getenv("GIN_MODE") == "" {
		gin.SetMode(gin.DebugMode)
	}

	// Create Gin engine
	engine := gin.Default()

	// Setup Swagger
	docs.SwaggerInfo.BasePath = "/api/v1"
	docs.SwaggerInfo.Host = fmt.Sprintf("localhost:%s", cfg.ServerPort)
	engine.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	// Setup routes
	router.SetupRoutes(engine, db, redisClient)

	// Start server
	addr := fmt.Sprintf(":%s", cfg.ServerPort)
	log.Printf("Starting server on %s", addr)
	if err := engine.Run(addr); err != nil {
		log.Fatalf("Server error: %v", err)
	}
}
