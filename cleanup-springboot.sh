#!/bin/bash

# Spring Boot 文件清理脚本 - Linux/macOS
# 从 Go 项目中删除所有 Java/Maven 相关文件

# 颜色定义
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
BOLD='\033[1m'
NC='\033[0m' # No Color

# 打印函数
print_header() {
    echo -e "${BLUE}${BOLD}"
    echo "╔════════════════════════════════════════════════════════╗"
    echo "║       Spring Boot 文件清理工具 - Go 版本化              ║"
    echo "║   Remove Java/Maven Files from Go Project             ║"
    echo "╚════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

print_section() {
    echo -e "\n${BOLD}${BLUE}$1${NC}"
    echo "--------------------------------------------------------------"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

# 检查 Git 分支
check_git_branch() {
    print_section "Git 分支检查"
    
    if ! command -v git &> /dev/null; then
        print_error "Git 未安装"
        return 1
    fi
    
    BRANCH=$(git rev-parse --abbrev-ref HEAD 2>/dev/null)
    if [ $? -eq 0 ]; then
        print_info "当前分支: ${BOLD}${BRANCH}${NC}"
        if [[ "$BRANCH" == "go-clean" ]] || [[ "$BRANCH" == "go-refactor" ]] || [[ "$BRANCH" == "main" ]]; then
            print_success "✓ 分支正确"
            return 0
        else
            print_warning "建议在 'go-clean' 或 'go-refactor' 分支上执行此操作"
            read -p "继续吗? (y/n): " -n 1 -r
            echo
            [[ $REPLY =~ ^[Yy]$ ]]
            return $?
        fi
    else
        print_error "无法检查 Git 分支"
        return 1
    fi
}

# 检查 Go 文件
check_go_files() {
    print_section "Go 项目文件检查"
    
    local required_files=("go.mod" "cmd/main.go" "Dockerfile" "docker-compose.yml")
    local all_exist=true
    
    for file in "${required_files[@]}"; do
        if [ -f "$file" ]; then
            print_success "找到: $file"
        else
            print_error "缺失: $file"
            all_exist=false
        fi
    done
    
    if [ "$all_exist" = true ]; then
        print_success "✓ 所有关键 Go 文件都存在"
        return 0
    else
        print_error "某些 Go 文件缺失，请检查项目结构"
        return 1
    fi
}

# 获取待删除文件
get_files_to_delete() {
    local files=()
    
    # 检查单个文件
    for file in "pom.xml" "mvnw" "mvnw.cmd" "README.cn.md"; do
        if [ -f "$file" ]; then
            files+=("$file")
        fi
    done
    
    # 检查目录
    for dir in ".mvn" "src"; do
        if [ -d "$dir" ]; then
            files+=("$dir")
        fi
    done
    
    echo "${files[@]}"
}

# 显示待删除文件
show_files_to_delete() {
    print_section "待删除文件清单"
    
    local files=($1)
    local count=${#files[@]}
    
    if [ $count -eq 0 ]; then
        print_warning "未发现需要删除的 Spring Boot 文件"
        print_info "项目已是纯 Go 项目"
        return 1
    fi
    
    echo ""
    echo "将删除以下 $count 个项目:"
    echo ""
    
    local i=1
    for file in "${files[@]}"; do
        if [ -d "$file" ]; then
            echo "  $i. ${RED}📁 $file/${NC}"
        else
            echo "  $i. ${RED}📄 $file${NC}"
        fi
        ((i++))
    done
    
    echo ""
    echo -e "${YELLOW}${BOLD}警告:${NC} 此操作将永久删除这些文件"
    echo "可以通过 'git checkout' 从历史记录中恢复"
    echo ""
    
    read -p "确定删除这些文件吗? (yes/no): " response
    [[ "$response" == "yes" ]]
    return $?
}

# 删除文件
delete_files() {
    print_section "删除文件"
    
    local files=($1)
    local deleted=0
    local errors=0
    
    for file in "${files[@]}"; do
        if [ -d "$file" ]; then
            if rm -rf "$file" 2>/dev/null; then
                print_success "删除目录: $file"
                ((deleted++))
            else
                print_error "删除 $file 失败"
                ((errors++))
            fi
        else
            if rm -f "$file" 2>/dev/null; then
                print_success "删除文件: $file"
                ((deleted++))
            else
                print_error "删除 $file 失败"
                ((errors++))
            fi
        fi
    done
    
    echo "$deleted $errors"
}

# 显示项目结构
show_project_structure() {
    print_section "清理后的项目结构"
    echo ""
    cat << 'EOF'
  accountant-meow-backend/
  ├── cmd/
  │   └── main.go
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
  ├── docs/
  ├── .github/
  ├── go.mod
  ├── go.sum
  ├── Dockerfile
  ├── docker-compose.yml
  ├── .env.example
  ├── Makefile
  ├── .gitignore
  ├── README.md
  ├── README-GO.md
  ├── LICENSE
  └── ...
EOF
}

# 显示后续步骤
show_next_steps() {
    print_section "后续步骤"
    cat << 'EOF'

1. 提交更改到 Git:
   git add .
   git commit -m "remove: delete Spring Boot architecture files"

2. 推送到远程:
   git push origin go-clean

3. 启动应用:
   make docker-up

4. 测试 API:
   curl http://localhost:8080/api/v1/public/health

5. 查看 Swagger 文档:
   open http://localhost:8080/swagger/index.html

6. 设置默认分支 (在 GitHub):
   Settings → Default branch → go-clean

EOF
}

# 显示总结
show_summary() {
    local result=$1
    local deleted=$(echo $result | cut -d' ' -f1)
    local errors=$(echo $result | cut -d' ' -f2)
    
    print_section "执行总结"
    
    if [ "$deleted" -gt 0 ]; then
        print_success "成功删除 $deleted 个项目"
    fi
    
    if [ "$errors" -gt 0 ]; then
        print_warning "删除过程中出现 $errors 个错误"
        return 1
    else
        print_success "✓ 清理完成，无任何错误"
        return 0
    fi
}

# 主函数
main() {
    print_header
    
    # 检查 Git 分支
    if ! check_git_branch; then
        print_error "\n已取消操作"
        return 1
    fi
    
    echo ""
    
    # 检查 Go 文件
    if ! check_go_files; then
        print_error "\n项目结构不完整，请检查"
        return 1
    fi
    
    echo ""
    
    # 获取待删除文件
    files_to_delete=$(get_files_to_delete)
    
    # 显示待删除文件
    if ! show_files_to_delete "$files_to_delete"; then
        print_info "\n操作已取消"
        return 0
    fi
    
    echo ""
    
    # 删除文件
    result=$(delete_files "$files_to_delete")
    
    echo ""
    
    # 显示项目结构
    show_project_structure
    
    echo ""
    
    # 显示总结
    if ! show_summary "$result"; then
        return 1
    fi
    
    echo ""
    
    # 显示后续步骤
    show_next_steps
    
    echo ""
    echo -e "${GREEN}${BOLD}✓ 清理完成！${NC}"
    echo -e "\n查看详细信息请阅读: ${BOLD}CLEANUP_SPRINGBOOT.md${NC}\n"
    
    return 0
}

# 执行主函数
main
exit $?
