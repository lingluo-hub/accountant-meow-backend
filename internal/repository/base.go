package repository

import (
	"gorm.io/gorm"
)

// BaseRepository provides common database operations
type BaseRepository struct {
	db *gorm.DB
}

// NewBaseRepository creates a new base repository
func NewBaseRepository(db *gorm.DB) *BaseRepository {
	return &BaseRepository{db: db}
}

// GetDB returns the database connection
func (r *BaseRepository) GetDB() *gorm.DB {
	return r.db
}

// Create creates a new record
func (r *BaseRepository) Create(value interface{}) error {
	return r.db.Create(value).Error
}

// Update updates a record
func (r *BaseRepository) Update(value interface{}) error {
	return r.db.Save(value).Error
}

// Delete deletes a record (soft delete)
func (r *BaseRepository) Delete(value interface{}) error {
	return r.db.Delete(value).Error
}

// FindByID finds a record by ID
func (r *BaseRepository) FindByID(id uint, dest interface{}) error {
	return r.db.First(dest, id).Error
}

// FindAll finds all records
func (r *BaseRepository) FindAll(dest interface{}) error {
	return r.db.Find(dest).Error
}
