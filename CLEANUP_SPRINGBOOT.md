# Spring Boot 清理说明

本文档说明如何从 Java Spring Boot 版本清理出纯 Go 项目结构。

## 📋 待删除文件清单

以下是从项目中删除的 Spring Boot 相关文件：

### 高优先级（核心 Spring Boot 文件）

- ❌ `pom.xml` - Maven 项目配置文件
- ❌ `src/` - 整个 Java 源代码目录
- ❌ `.mvn/` - Maven Wrapper 配置目录

### 中优先级（构建脚本）

- ❌ `mvnw` - Maven Wrapper for Linux/macOS
- ❌ `mvnw.cmd` - Maven Wrapper for Windows

### 低优先级（文档）

- ❌ `README.cn.md` - Java 版本的中文文档

---

## ✅ 保留文件

### Go 应用代码
```
✅ cmd/main.go
✅ internal/
✅ go.mod
✅ go.sum
```

### 部署和配置
```
✅ Dockerfile (Go 版本)
✅ docker-compose.yml
✅ .env.example
✅ Makefile
```

### 文档
```
✅ README-GO.md (新的 Go 文档)
✅ README.md (通用说明)
✅ CODE_OF_CONDUCT.md
✅ CONTRIBUTING.md
```

### 数据库
```
✅ migrations/
✅ docs/
```

### 其他
```
✅ .gitignore (Go 版本)
✅ LICENSE
✅ .github/
```

---

## 🔄 清理流程

### 自动化清理（推荐）

#### 1. 使用 Python 脚本（所有平台）

```bash
python3 cleanup-springboot.py
```

优点：
- ✅ 跨平台兼容
- ✅ 自动验证
- ✅ 详细日志
- ✅ 易于理解

#### 2. 使用 Bash 脚本（Linux/macOS）

```bash
chmod +x cleanup-springboot.sh
./cleanup-springboot.sh
```

#### 3. 使用 Batch 脚本（Windows）

```powershell
.\cleanup-springboot.bat
```

### 手动清理

```bash
# 1. 切换到 go-clean 分支
git checkout go-clean

# 2. 删除 Maven 相关文件
rm -f pom.xml mvnw mvnw.cmd README.cn.md

# 3. 删除 Maven 配置目录
rm -rf .mvn

# 4. 删除 Java 源代码
rm -rf src

# 5. 提交更改
git add .
git commit -m "remove: delete Spring Boot architecture files"

# 6. 推送
git push origin go-clean
```

---

## ✨ 清理后的项目结构

```
accountant-meow-backend/
├── cmd/
│   └── main.go                      # 应用入口
├── internal/
│   ├── config/
│   ├── database/
│   ├── handler/
│   ├── middleware/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── router/
├── migrations/
│   ├── 000001_init_schema.up.sql
│   └── 000001_init_schema.down.sql
├── docs/
│   └── docs.go
├── .github/                         # GitHub Actions
├── go.mod                           # Go 模块定义
├── go.sum                           # 依赖校验
├── Dockerfile                       # Go Docker 构建
├── docker-compose.yml               # 容器编排
├── .env.example                     # 环境变量模板
├── Makefile                         # 开发命令
├── .gitignore                       # Git 忽略规则
├── README.md                        # 通用文档
├── README-GO.md                     # Go 详细文档
├── CODE_OF_CONDUCT.md
├── CONTRIBUTING.md
└── LICENSE
```

---

## 📊 版本对比

| 方面 | Java 版本 | Go 版本 |
|-----|---------|--------|
| **主要文件** | pom.xml, src/ | go.mod, cmd/, internal/ |
| **构建工具** | Maven (mvn) | Go Build |
| **构建脚本** | mvnw, mvnw.cmd | make |
| **容器大小** | ~800MB | ~80MB |
| **启动时间** | ~5-10s | ~1-2s |
| **内存占用** | ~500MB | ~50MB |
| **并发性能** | 低 | 高 |

---

## 🔍 验证清理

### 检查文件是否删除

```bash
# 列出项目结构（不显示 .git）
tree -a -I '.git' -L 2

# 或
ls -la
```

### 检查 Git 状态

```bash
# 显示待提交的更改
git status

# 显示删除的文件
git status | grep deleted
```

### 验证 Go 项目完整性

```bash
# 检查 Go 版本
go version

# 下载依赖
go mod download

# 验证依赖
go mod verify

# 构建检查
go build -v ./...
```

---

## 🚀 清理后立即开始

### 1. 启动应用

```bash
make docker-up
```

### 2. 测试 API

```bash
# 健康检查
curl http://localhost:8080/api/v1/public/health

# Swagger 文档
open http://localhost:8080/swagger/index.html
```

### 3. 查看日志

```bash
make docker-logs
```

### 4. 停止容器

```bash
make docker-down
```

---

## 🔄 分支管理建议

### 保留分支历史

```bash
# 创建标签保存 Java 版本
git tag -a v1.x-java-springboot -m "Last Java version"
git push origin v1.x-java-springboot
```

### 设置默认分支

1. 访问 GitHub 仓库设置
2. 找到 "Default branch"
3. 改为 "go-clean"
4. 保存

### 存档 Java 版本分支

```bash
# 重命名为存档分支
git branch -m next archived/v1.x-java-springboot
git push -u origin archived/v1.x-java-springboot
git push origin :next
```

---

## 📝 常见问题

### Q: 如何恢复删除的文件？

A: 从 Git 历史恢复
```bash
git checkout next -- <文件路径>
```

### Q: Maven 缓存在哪里？

A: 本地 Maven 缓存位置
- Linux/macOS: `~/.m2/repository/`
- Windows: `%userprofile%\.m2\repository\`

清理：
```bash
rm -rf ~/.m2/repository/
```

### Q: 如何从 Java 版本迁移数据？

A: 使用数据库备份
```bash
# 导出 Java 版本数据
pg_dump accountant_meow > backup.sql

# 在 Go 版本中导入
psql accountant_meow < backup.sql
```

### Q: 旧的 GitHub 工作流文件怎么办？

A: 检查并更新 `.github/workflows/`
```bash
ls -la .github/workflows/
```

将 Maven 相关的工作流改为 Go 构建步骤。

---

## 🎯 完整迁移检查清单

- [ ] 删除 pom.xml
- [ ] 删除 src/ 目录
- [ ] 删除 .mvn/ 目录
- [ ] 删除 mvnw 和 mvnw.cmd
- [ ] 删除 README.cn.md
- [ ] 验证 Go 文件完整
- [ ] 验证 Dockerfile 正确
- [ ] 验证 docker-compose.yml 正确
- [ ] 验证 go.mod 和 go.sum 存在
- [ ] 测试 make docker-up
- [ ] 测试健康检查 API
- [ ] 提交 Git 更改
- [ ] 推送到远程
- [ ] 设置默认分支
- [ ] 存档 Java 版本分支
- [ ] 更新 GitHub 项目描述

---

## 📞 需要帮助？

- 📖 查看 README-GO.md 获取开发指南
- 🐛 在 GitHub Issues 报告问题
- 💬 在 GitHub Discussions 讨论
- 🔗 项目主页：https://github.com/lingluo-hub/accountant-meow-backend

---

**最后更新**：2026-04-30
