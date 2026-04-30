package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

// HealthCheckResponse represents the health check response
type HealthCheckResponse struct {
	Status  string `json:"status" example:"ok"`
	Version string `json:"version" example:"2.0.0"`
}

// HealthCheck godoc
// @Summary      Health check
// @Description  Check if the service is running
// @Tags         public
// @Accept       json
// @Produce      json
// @Success      200  {object}  HealthCheckResponse
// @Router       /public/health [get]
func HealthCheck(c *gin.Context) {
	c.JSON(http.StatusOK, HealthCheckResponse{
		Status:  "ok",
		Version: "2.0.0",
	})
}
