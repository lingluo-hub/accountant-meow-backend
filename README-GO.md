# Accountant Meow Backend - Go 版本

这是 Accountant Meow（记账喵）后端的 Go 语言重构版本。

## 📋 项目概览

- **语言**：Go 1.21+
- **Web 框架**：Gin
- **数据库**：PostgreSQL 15
- **缓存**：Redis 7
- **API 文档**：Swagger 2.0
- **容器化**：Docker & Docker Compose

## 🎯 技术栈

| 组件 | 库 | 版本 |
|-----|-----|------|
| Web Framework | Gin | 1.9+ |
| ORM | GORM | 1.25+ |
| Database Driver | postgres | 1.5+ |
| Cache Client | go-redis | 9.1+ |
| API Documentation | Swagger | 1.16+ |
| Logging | Zap | 1.26+ |
| Validation | validator | 10.15+ |
| Migration | golang-migrate | 4.16+ |
| Environment Config | godotenv | 1.5+ |

## 📁 项目结构

```
accountant-meow-backend/
├─�� cmd/
│   └── main.go                 # 应用入口
├── internal/
│   ├── config/
│   │   └── config.go           # 配置管理
│   ├── database/
│   │   ├── database.go         # PostgreSQL 初始化
│   │   └── redis.go            # Redis 初始化
│   ├── handler/
│   │   └── health.go           # HTTP 处理器
│   ├── middleware/
│   │   └── auth.go             # 认证中间件
│   ├── model/
│   │   └── base.go             # 数据模型
│   ├── repository/
│   │   └── base.go             # 数据访问层
│   ├── service/
│   │   └── base.go             # 业务逻辑层
│   └── router/
│       └── router.go           # 路由配置
├── migrations/
│   ├── 000001_init_schema.up.sql    # 初始化迁移
│   └── 000001_init_schema.down.sql  # 回滚迁移
├── docs/
│   └── docs.go                 # Swagger 文档
├── Dockerfile                  # Docker 构建
├── docker-compose.yml          # Docker Compose
├── go.mod                      # Go 模块定义
├── .env.example                # 环境变量模板
├── Makefile                    # 开发命令
└── README-GO.md                # 本文件
```

## 🚀 快速开始

### 前置要求

- Go 1.21 或更高版本
- Docker & Docker Compose（可选，用于容器化开发环境）
- PostgreSQL 15（如果不使用 Docker）
- Redis 7（如果不使用 Docker）

### 本地开发环境设置

#### 1. 克隆项目并切换分支

```bash
git clone https://github.com/lingluo-hub/accountant-meow-backend.git
cd accountant-meow-backend
git checkout go-refactor
```

#### 2. 复制环境变量文件

```bash
cp .env.example .env
```

#### 3. 使用 Docker Compose 启动依赖服务（推荐）

```bash
make docker-up
```

这将启动：
- PostgreSQL 数据库
- Redis 缓存
- Go 应用程序

#### 4. 如果本地已有数据库，修改 `.env` 文件

```bash
# 修改以下变量指向您的数据库
DB_HOST=your-db-host
DB_PORT=5432
DB_USER=your-db-user
DB_PASSWORD=your-db-password
REDIS_HOST=your-redis-host
REDIS_PORT=6379
```

#### 5. 下载依赖并运行

```bash
make deps
make run
```

### 使用 Docker Compose（推荐）

```bash
# 启动所有服务
make docker-up

# 查看日志
make docker-logs

# 停止服务
make docker-down

# 完全清理（包括数据）
make docker-clean
```

## 📚 API 文档

应用启动后，访问 Swagger UI：

```
http://localhost:8080/swagger/index.html
```

### 健康检查（公开端点）

```bash
curl http://localhost:8080/api/v1/public/health
```

响应：

```json
{
  "status": "ok",
  "version": "2.0.0"
}
```

### 认证请求（需要 Basic Auth）

默认凭证：
- 用户名：`admin`
- 密码：`admin123`

```bash
# 使用 Basic Auth 访问受保护的端点
curl -u admin:admin123 http://localhost:8080/api/v1/accounts
```

或设置请求头：

```bash
curl -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" http://localhost:8080/api/v1/accounts
```

## 🗄️ 数据库

### 数据库架构

```sql
-- 用户表
users (id, username, email, password, avatar_url, created_at, updated_at, deleted_at)

-- 账户表
accounts (id, user_id, name, account_type, balance, currency, description, created_at, updated_at, deleted_at)

-- 分类表
categories (id, user_id, name, type, icon, color, created_at, updated_at, deleted_at)

-- 交易表
transactions (id, user_id, account_id, category_id, amount, type, description, transaction_date, tags, created_at, updated_at, deleted_at)
```

### 数据库迁移

迁移脚本位于 `migrations/` 目录，使用 `golang-migrate` 工具管理。

#### 查看迁移文件

```bash
ls -la migrations/
```

#### 手动运行迁移

```bash
# 需要先安装 migrate CLI
make migrate

# 回滚迁移
make migrate-down
```

## 🔧 开发命令

### Makefile 命令

```bash
# 显示帮助
make help

# 构建
make build

# 运行
make run

# 测试
make test

# 代码格式化
make fmt

# 下载依赖
make deps

# 生成 Swagger 文档
make swagger

# Docker 命令
make docker-build
make docker-up
make docker-down
make docker-logs
make docker-clean
```

### 手动命令

```bash
# 安装依赖
go mod download
go mod tidy

# 构建
go build -o bin/accountant-meow-backend ./cmd/main.go

# 运行
go run ./cmd/main.go

# 测试
go test -v ./...
go test -v -cover ./...

# 代码检查
go fmt ./...
go vet ./...

# 生成 Swagger 文档
swag init -g cmd/main.go
```

## 🔐 环境变量

| 变量名 | 默认值 | 说明 |
|--------|--------|------|
| SERVER_PORT | 8080 | 服务器端口 |
| SERVER_ENV | development | 运行环境（development/production） |
| GIN_MODE | debug | Gin 框架模式（debug/release） |
| DB_HOST | localhost | 数据库主机 |
| DB_PORT | 5432 | 数据库端口 |
| DB_NAME | accountant_meow | 数据库名称 |
| DB_USER | postgres | 数据库用户 |
| DB_PASSWORD | postgres | 数据库密码 |
| DB_SSLMODE | disable | PostgreSQL SSL 模式 |
| REDIS_HOST | localhost | Redis 主机 |
| REDIS_PORT | 6379 | Redis 端口 |
| REDIS_PASSWORD | redis123 | Redis 密码 |
| REDIS_DB | 0 | Redis 数据库编号 |
| AUTH_USERNAME | admin | 基本认证用户名 |
| AUTH_PASSWORD | admin123 | 基本认证密码 |

## 🐳 Docker 部署

### 构建 Docker 镜像

```bash
make docker-build
```

### 运行单个容器

```bash
docker run -p 8080:8080 --env-file .env accountant-meow-backend:latest
```

### 使用 Docker Compose

```bash
docker-compose up -d
```

### 查看容器日志

```bash
docker-compose logs -f app
```

### 进入容器

```bash
docker-compose exec app sh
```

## 🧪 测试

### 运行所有测试

```bash
make test
```

### 生成覆盖率报告

```bash
make test-coverage
```

## 📊 项目架构

```
HTTP Request
    ↓
[Router] - 路由配置
    ↓
[Middleware] - 认证、CORS 等
    ↓
[Handler] - HTTP 处理器
    ↓
[Service] - 业务逻辑
    ↓
[Repository] - 数据访问
    ↓
[Database/Cache] - PostgreSQL & Redis
```

## 📈 性能优化

1. **数据库连接池**
   - 最大连接数：100
   - 最小空闲连接：10
   - 连接最大生存时间：1 小时

2. **Redis 连接池**
   - 连接池大小：10
   - 最小空闲连接：5
   - 读超时：3 秒
   - 写超时：3 秒

3. **数据库索引**
   - 用户 ID 索引
   - 账户 ID 索引
   - 交易日期索引
   - 软删除标记索引

## 🐛 故障排查

### 连接错误

**错误**：`connection refused`

**解决方案**：
1. 检查 PostgreSQL 和 Redis 是否运行
2. 验证环境变量中��主机和端口
3. 检查防火墙设置

### 数据库迁移失败

**错误**：`migration failed`

**解决方案**：
1. 检查 migrations/ 目录中的 SQL 脚本
2. 验证数据库连接
3. 检查数据库权限

### 认证失败

**错误**：`unauthorized`

**解决方案**：
1. 验证 AUTH_USERNAME 和 AUTH_PASSWORD
2. 检查 Basic Auth 头格式
3. 确保使用 HTTPS 在生产环境

## 📝 日志

应用使用 Zap 日志库，支持：

- 结构化日志
- 多个日志级别（DEBUG, INFO, WARN, ERROR)
- 文件和控制台输出

## 🔄 从 Java 版本迁移

### API 端点对应关系

| 功能 | Java 端点 | Go 端点 |
|-----|-----------|----------|
| 健康检查 | `/actuator/health` | `/api/v1/public/health` |
| 获取账户列表 | `GET /accounts` | `GET /api/v1/accounts` |
| 创建账户 | `POST /accounts` | `POST /api/v1/accounts` |
| 获取交易列表 | `GET /transactions` | `GET /api/v1/transactions` |
| 创建交易 | `POST /transactions` | `POST /api/v1/transactions` |

## 🤝 贡献指南

1. 创建特性分支：`git checkout -b feature/your-feature`
2. 提交更改：`git commit -am 'Add your feature'`
3. 推送到分支：`git push origin feature/your-feature`
4. 提交 Pull Request

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。n
## 📞 联系方式

- 项目地址：[GitHub](https://github.com/lingluo-hub/accountant-meow-backend)
- 问题报告：[Issues](https://github.com/lingluo-hub/accountant-meow-backend/issues)
- 讨论：[Discussions](https://github.com/lingluo-hub/accountant-meow-backend/discussions)

## 🎯 后续计划

- [ ] 实现完整的账户管理 API
- [ ] 实现交易管理 API
- [ ] 实现分类管理 API
- [ ] 添加单元测试和集成测试
- [ ] 实现数据统计和报表功能
- [ ] 添加数据导入/导出功能
- [ ] 国际化支持
- [ ] 性能优化和压测

---

**最后更新**：2026-04-30

**版本**：2.0.0-Go
