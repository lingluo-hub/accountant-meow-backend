package model

import (
	"time"

	"gorm.io/gorm"
)

// BaseModel represents common fields for all models
type BaseModel struct {
	ID        uint           `gorm:"primaryKey" json:"id" example:"1"`
	CreatedAt time.Time      `gorm:"autoCreateTime" json:"created_at"`
	UpdatedAt time.Time      `gorm:"autoUpdateTime" json:"updated_at"`
	DeletedAt gorm.DeletedAt `gorm:"index" json:"-"`
}

// Response represents a standard API response
type Response struct {
	Code    int         `json:"code" example:"200"`
	Message string      `json:"message" example:"success"`
	Data    interface{} `json:"data"`
}

// PaginatedResponse represents a paginated API response
type PaginatedResponse struct {
	Code    int         `json:"code" example:"200"`
	Message string      `json:"message" example:"success"`
	Data    interface{} `json:"data"`
	Page    int         `json:"page" example:"1"`
	Size    int         `json:"size" example:"10"`
	Total   int64       `json:"total" example:"100"`
}

// ErrorResponse represents an error response
type ErrorResponse struct {
	Code    int    `json:"code" example:"400"`
	Message string `json:"message" example:"bad request"`
}
