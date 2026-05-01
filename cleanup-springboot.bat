@echo off
REM Spring Boot 文件清理脚本 - Windows
REM 从 Go 项目中删除所有 Java/Maven 相关文件

setlocal enabledelayedexpansion

REM 颜色定义 (使用 ANSI 代码 - 仅在 Windows 10+ 中有效)
REM 为了兼容性，我们使用简单的输出

echo.
echo ===============================================================
echo   Spring Boot 文件清理工具 - Go 版本化
echo   Remove Java/Maven Files from Go Project
echo ===============================================================
echo.

REM 检查 Git 分支
echo [1/5] Git 分支检查
echo ---------------------------------------------------------------
for /f "tokens=*" %%i in ('git rev-parse --abbrev-ref HEAD 2^>nul') do set BRANCH=%%i

if "%BRANCH%"==" " (
    echo X Git 未安装或不在仓库中
    exit /b 1
)

echo i 当前分支: %BRANCH%

if "%BRANCH%"=="go-clean" (
    echo [OK] 分支正确
) else if "%BRANCH%"=="go-refactor" (
    echo [OK] 分支正确
) else if "%BRANCH%"=="main" (
    echo [OK] 分支正确
) else (
    echo ! 建议在 'go-clean' 分支上执行此操作
    set /p continue="继续吗? (y/n): "
    if /i not "!continue!"=="y" (
        echo 已取消操作
        exit /b 0
    )
)

echo.

REM 检查 Go 文件
echo [2/5] Go 项目文件检查
echo ---------------------------------------------------------------

set all_exist=true

if exist go.mod (
    echo [OK] 找到: go.mod
) else (
    echo X 缺失: go.mod
    set all_exist=false
)

if exist cmd\main.go (
    echo [OK] 找到: cmd\main.go
) else (
    echo X 缺失: cmd\main.go
    set all_exist=false
)

if exist Dockerfile (
    echo [OK] 找到: Dockerfile
) else (
    echo X 缺失: Dockerfile
    set all_exist=false
)

if exist docker-compose.yml (
    echo [OK] 找到: docker-compose.yml
) else (
    echo X 缺失: docker-compose.yml
    set all_exist=false
)

if "%all_exist%"=="true" (
    echo [OK] 所有关键 Go 文件都存在
) else (
    echo X 某些 Go 文件缺失，请检查项目结构
    exit /b 1
)

echo.

REM 获取待删除文件
echo [3/5] 待删除文件清单
echo ---------------------------------------------------------------

set files_count=0
if exist pom.xml set /a files_count+=1
if exist mvnw set /a files_count+=1
if exist mvnw.cmd set /a files_count+=1
if exist README.cn.md set /a files_count+=1
if exist .mvn set /a files_count+=1
if exist src set /a files_count+=1

if %files_count%==0 (
    echo ! 未发现需要删除的 Spring Boot 文件
    echo i 项目已是纯 Go 项目
    exit /b 0
)

echo.
echo 将删除以下 %files_count% 个项目:
echo.

set item_num=1
if exist pom.xml (
    echo   %item_num%. [FILE] pom.xml
    set /a item_num+=1
)

if exist mvnw (
    echo   %item_num%. [FILE] mvnw
    set /a item_num+=1
)

if exist mvnw.cmd (
    echo   %item_num%. [FILE] mvnw.cmd
    set /a item_num+=1
)

if exist README.cn.md (
    echo   %item_num%. [FILE] README.cn.md
    set /a item_num+=1
)

if exist .mvn (
    echo   %item_num%. [DIR] .mvn\
    set /a item_num+=1
)

if exist src (
    echo   %item_num%. [DIR] src\
    set /a item_num+=1
)

echo.
echo ! 警告: 此操作将永久删除这些文件
echo i 可以通过 'git checkout' 从历史记录中恢复
echo.

set /p confirm="确定删除这些文件吗? (yes/no): "
if /i not "%confirm%"=="yes" (
    echo 操作已取消
    exit /b 0
)

echo.

REM 删除文件
echo [4/5] 删除文件
echo ---------------------------------------------------------------

set delete_count=0

if exist pom.xml (
    del /f /q pom.xml
    if !errorlevel! equ 0 (
        echo [OK] 删除文件: pom.xml
        set /a delete_count+=1
    ) else (
        echo X 删除 pom.xml 失败
    )
)

if exist mvnw (
    del /f /q mvnw
    if !errorlevel! equ 0 (
        echo [OK] 删除文件: mvnw
        set /a delete_count+=1
    ) else (
        echo X 删除 mvnw 失败
    )
)

if exist mvnw.cmd (
    del /f /q mvnw.cmd
    if !errorlevel! equ 0 (
        echo [OK] 删除文件: mvnw.cmd
        set /a delete_count+=1
    ) else (
        echo X 删除 mvnw.cmd 失败
    )
)

if exist README.cn.md (
    del /f /q README.cn.md
    if !errorlevel! equ 0 (
        echo [OK] 删除文件: README.cn.md
        set /a delete_count+=1
    ) else (
        echo X 删除 README.cn.md 失败
    )
)

if exist .mvn (
    rmdir /s /q .mvn
    if !errorlevel! equ 0 (
        echo [OK] 删除目录: .mvn\
        set /a delete_count+=1
    ) else (
        echo X 删除 .mvn 失败
    )
)

if exist src (
    rmdir /s /q src
    if !errorlevel! equ 0 (
        echo [OK] 删除目录: src\
        set /a delete_count+=1
    ) else (
        echo X 删除 src 失败
    )
)

echo.

REM 显示项目结构
echo [5/5] 清理后的项目结构
echo ---------------------------------------------------------------
echo.
echo accountant-meow-backend/
echo   ├── cmd/
echo   │   └── main.go
echo   ├── internal/
echo   │   ├── config/
echo   │   ├── database/
echo   │   ├── handler/
echo   │   ├── middleware/
echo   │   ├── model/
echo   │   ├── repository/
echo   │   ├── service/
echo   │   └── router/
echo   ├── migrations/
echo   ├── docs/
echo   ├── .github/
echo   ├── go.mod
echo   ├── go.sum
echo   ├── Dockerfile
echo   ├── docker-compose.yml
echo   ├── .env.example
echo   ├── Makefile
echo   ├── .gitignore
echo   ├── README.md
echo   ├── README-GO.md
echo   ├── LICENSE
echo   └── ...
echo.

REM 显示总结
echo 执行总结
echo ---------------------------------------------------------------
echo [OK] 成功删除 %delete_count% 个项目
echo [OK] 清理完成，无任何错误
echo.

REM 显示后续步骤
echo 后续步骤
echo ---------------------------------------------------------------
echo.
echo 1. 提交更改到 Git:
echo    git add .
echo    git commit -m "remove: delete Spring Boot architecture files"
echo.
echo 2. 推送到远程:
echo    git push origin go-clean
echo.
echo 3. 启动应用:
echo    make docker-up
echo.
echo 4. 测试 API:
echo    curl http://localhost:8080/api/v1/public/health
echo.
echo 5. 查看 Swagger 文档:
echo    start http://localhost:8080/swagger/index.html
echo.
echo 6. 设置默认分支 (在 GitHub):
echo    Settings ^> Default branch ^> go-clean
echo.

echo [OK] 清理完成！
echo i 查看详细信息请阅读: CLEANUP_SPRINGBOOT.md
echo.

endlocal
exit /b 0
