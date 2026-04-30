package service

import (
	"github.com/redis/go-redis/v9"
	"gorm.io/gorm"

	"github.com/lingluo-hub/accountant-meow-backend/internal/repository"
)

// BaseService provides common business logic operations
type BaseService struct {
	db     *gorm.DB
	redis  *redis.Client
	repo   *repository.BaseRepository
}

// NewBaseService creates a new base service
func NewBaseService(db *gorm.DB, redis *redis.Client) *BaseService {
	return &BaseService{
		db:    db,
		redis: redis,
		repo:  repository.NewBaseRepository(db),
	}
}

// GetDB returns the database connection
func (s *BaseService) GetDB() *gorm.DB {
	return s.db
}

// GetRedis returns the redis client
func (s *BaseService) GetRedis() *redis.Client {
	return s.redis
}

// GetRepository returns the repository
func (s *BaseService) GetRepository() *repository.BaseRepository {
	return s.repo
}
