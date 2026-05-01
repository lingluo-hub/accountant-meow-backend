#!/usr/bin/env python3
"""
Spring Boot 文件清理工具
从 Go 项目中删除所有 Java/Maven 相关文件

Usage:
    python3 cleanup-springboot.py
"""

import os
import sys
from pathlib import Path
from typing import List, Tuple

# ANSI 颜色代码
class Colors:
    GREEN = '\033[92m'
    RED = '\033[91m'
    YELLOW = '\033[93m'
    BLUE = '\033[94m'
    BOLD = '\033[1m'
    END = '\033[0m'

def print_header():
    """打印程序头"""
    print(f"{Colors.BLUE}{Colors.BOLD}")
    print("╔════════════════════════════════════════════════════════╗")
    print("║       Spring Boot 文件清理工具 - Go 版本化              ║")
    print("║   Remove Java/Maven Files from Go Project             ║")
    print("╚════════════════════════════════════════════════════════╝")
    print(f"{Colors.END}")

def print_section(title: str):
    """打印章节标题"""
    print(f"\n{Colors.BOLD}{Colors.BLUE}{title}{Colors.END}")
    print("-" * 60)

def print_success(msg: str):
    """打印成功消息"""
    print(f"{Colors.GREEN}✓ {msg}{Colors.END}")

def print_error(msg: str):
    """打印错误消息"""
    print(f"{Colors.RED}✗ {msg}{Colors.END}")

def print_warning(msg: str):
    """打印警告消息"""
    print(f"{Colors.YELLOW}⚠ {msg}{Colors.END}")

def print_info(msg: str):
    """打印信息消息"""
    print(f"{Colors.BLUE}ℹ {msg}{Colors.END}")

def check_git_branch() -> bool:
    """检查当前 Git 分支"""
    print_section("Git 分支检查")
    try:
        import subprocess
        result = subprocess.run(
            ["git", "rev-parse", "--abbrev-ref", "HEAD"],
            capture_output=True,
            text=True
        )
        branch = result.stdout.strip()
        if result.returncode == 0:
            print_info(f"当前分支: {Colors.BOLD}{branch}{Colors.END}")
            if branch in ["go-clean", "go-refactor", "main"]:
                print_success(f"✓ 分支正确")
                return True
            else:
                print_warning(f"建议在 'go-clean' 或 'go-refactor' 分支上执行此操作")
                response = input(f"\n继续吗? (y/n): ")
                return response.lower() == 'y'
        else:
            print_error("无法检查 Git 分支")
            return False
    except Exception as e:
        print_error(f"Git 检查失败: {e}")
        return False

def check_go_files() -> bool:
    """检查 Go 项目文件是否存在"""
    print_section("Go 项目文件检查")
    
    required_files = [
        "go.mod",
        "cmd/main.go",
        "internal/config/config.go",
        "Dockerfile",
        "docker-compose.yml",
    ]
    
    all_exist = True
    for file_path in required_files:
        if os.path.exists(file_path):
            print_success(f"找到: {file_path}")
        else:
            print_error(f"缺失: {file_path}")
            all_exist = False
    
    if all_exist:
        print_success("✓ 所有关键 Go 文件都存在")
    else:
        print_error("某些 Go 文件缺失，请检查项目结构")
    
    return all_exist

def get_files_to_delete() -> List[str]:
    """获取要删除的文件列表"""
    files_to_delete = []
    
    # 检查单个文件
    single_files = [
        "pom.xml",
        "mvnw",
        "mvnw.cmd",
        "README.cn.md",
    ]
    
    for file_path in single_files:
        if os.path.isfile(file_path):
            files_to_delete.append(file_path)
    
    # 检查目录
    directories = [
        ".mvn",
        "src",
    ]
    
    for dir_path in directories:
        if os.path.isdir(dir_path):
            files_to_delete.append(dir_path)
    
    return files_to_delete

def show_files_to_delete(files: List[str]) -> bool:
    """显示要删除的文件并请求确认"""
    print_section("待删除文件清单")
    
    if not files:
        print_warning("未发现需要删除的 Spring Boot 文件")
        print_info("项目已是纯 Go 项目")
        return False
    
    print(f"\n将删除以下 {len(files)} 个项目:\n")
    
    for i, file_path in enumerate(files, 1):
        if os.path.isdir(file_path):
            print(f"  {i}. {Colors.RED}📁 {file_path}/{Colors.END}")
        else:
            print(f"  {i}. {Colors.RED}📄 {file_path}{Colors.END}")
    
    print(f"\n{Colors.YELLOW}{Colors.BOLD}警告:{Colors.END} 此操作将永久删除这些文件")
    print(f"可以通过 'git checkout' 从历史记录中恢复\n")
    
    response = input(f"确定删除这些文件吗? (yes/no): ")
    return response.lower() == "yes"

def delete_files(files: List[str]) -> Tuple[int, List[str]]:
    """删除文件"""
    print_section("删除文件")
    
    deleted = 0
    errors = []
    
    for file_path in files:
        try:
            if os.path.isdir(file_path):
                import shutil
                shutil.rmtree(file_path)
                print_success(f"删除目录: {file_path}")
            else:
                os.remove(file_path)
                print_success(f"删除文件: {file_path}")
            deleted += 1
        except Exception as e:
            error_msg = f"删除 {file_path} 失败: {e}"
            print_error(error_msg)
            errors.append(error_msg)
    
    return deleted, errors

def show_project_structure():
    """显示清理后的项目结构"""
    print_section("清理后的项目结构")
    print()
    
    structure = [
        "accountant-meow-backend/",
        "├── cmd/",
        "│   └── main.go",
        "├── internal/",
        "│   ├── config/",
        "│   ├── database/",
        "│   ├── handler/",
        "│   ├── middleware/",
        "│   ├── model/",
        "│   ├── repository/",
        "│   ├── service/",
        "│   └── router/",
        "├── migrations/",
        "├── docs/",
        "├── .github/",
        "├── go.mod",
        "├── go.sum",
        "├── Dockerfile",
        "├── docker-compose.yml",
        "├── .env.example",
        "├── Makefile",
        "├── .gitignore",
        "├── README.md",
        "├── README-GO.md",
        "├── LICENSE",
        "└── ...",
    ]
    
    for line in structure:
        print(f"  {line}")

def show_next_steps():
    """显示后续步骤"""
    print_section("后续步骤")
    print(f"""
{Colors.BOLD}1. 提交更改到 Git:{Colors.END}
   git add .
   git commit -m "remove: delete Spring Boot architecture files"

{Colors.BOLD}2. 推送到远程:{Colors.END}
   git push origin go-clean

{Colors.BOLD}3. 启动应用:{Colors.END}
   make docker-up

{Colors.BOLD}4. 测试 API:{Colors.END}
   curl http://localhost:8080/api/v1/public/health

{Colors.BOLD}5. 查看 Swagger 文档:{Colors.END}
   open http://localhost:8080/swagger/index.html

{Colors.BOLD}6. 设置默认分支 (在 GitHub):{Colors.END}
   Settings → Default branch → go-clean
    """)

def show_summary(deleted: int, errors: List[str]):
    """显示总结"""
    print_section("执行总结")
    
    if deleted > 0:
        print_success(f"成功删除 {deleted} 个项目")
    
    if errors:
        print_warning(f"删除过程中出现 {len(errors)} 个错误:")
        for error in errors:
            print_error(f"  - {error}")
        return False
    else:
        print_success("✓ 清理完成，无任何错误")
        return True

def main():
    """主函数"""
    print_header()
    
    # 1. 检查 Git 分支
    if not check_git_branch():
        print_error("\n已取消操作")
        return 1
    
    print()
    
    # 2. 检查 Go 文件
    if not check_go_files():
        print_error("\n项目结构不完整，请检查")
        return 1
    
    print()
    
    # 3. 获取待删除文件
    files_to_delete = get_files_to_delete()
    
    # 4. 显示待删除文件并请求确认
    if not show_files_to_delete(files_to_delete):
        print_info("\n操作已取消")
        return 0
    
    print()
    
    # 5. 删除文件
    deleted, errors = delete_files(files_to_delete)
    
    print()
    
    # 6. 显示项目结构
    show_project_structure()
    
    print()
    
    # 7. 显示总结
    success = show_summary(deleted, errors)
    
    print()
    
    # 8. 显示后续步骤
    show_next_steps()
    
    print()
    print(f"{Colors.GREEN}{Colors.BOLD}✓ 清理完成！{Colors.END}")
    print(f"\n查看详细信息请阅读: {Colors.BOLD}CLEANUP_SPRINGBOOT.md{Colors.END}\n")
    
    return 0 if success else 1

if __name__ == "__main__":
    sys.exit(main())
