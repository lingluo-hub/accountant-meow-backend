package router

import (
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/redis/go-redis/v9"
	"gorm.io/gorm"

	"github.com/lingluo-hub/accountant-meow-backend/internal/config"
	"github.com/lingluo-hub/accountant-meow-backend/internal/handler"
	"github.com/lingluo-hub/accountant-meow-backend/internal/middleware"
)

// SetupRoutes configures all routes for the application
func SetupRoutes(engine *gin.Engine, db *gorm.DB, redis *redis.Client) {
	// Load configuration
	cfg := config.LoadConfig()

	// Setup CORS
	engine.Use(cors.New(cors.Config{
		AllowOrigins:     []string{"*"},
		AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Content-Type", "Authorization"},
		ExposeHeaders:    []string{"Content-Length"},
		AllowCredentials: true,
	}))

	// Public routes (no authentication required)
	public := engine.Group("/api/v1/public")
	{
		public.GET("/health", handler.HealthCheck)
	}

	// Protected routes (requires authentication)
	protected := engine.Group("/api/v1")
	protected.Use(middleware.BasicAuth(cfg.AuthUsername, cfg.AuthPassword))
	{
		// Account routes
		accounts := protected.Group("/accounts")
		{
			accounts.GET("", func(c *gin.Context) {
				c.JSON(200, gin.H{"message": "List accounts - Not implemented yet"})
			})
			accounts.POST("", func(c *gin.Context) {
				c.JSON(200, gin.H{"message": "Create account - Not implemented yet"})
			})
		}

		// Transaction routes
		transactions := protected.Group("/transactions")
		{
			transactions.GET("", func(c *gin.Context) {
				c.JSON(200, gin.H{"message": "List transactions - Not implemented yet"})
			})
			transactions.POST("", func(c *gin.Context) {
				c.JSON(200, gin.H{"message": "Create transaction - Not implemented yet"})
			})
		}

		// Category routes
		categories := protected.Group("/categories")
		{
			categories.GET("", func(c *gin.Context) {
				c.JSON(200, gin.H{"message": "List categories - Not implemented yet"})
			})
			categories.POST("", func(c *gin.Context) {
				c.JSON(200, gin.H{"message": "Create category - Not implemented yet"})
			})
		}
	}
}
