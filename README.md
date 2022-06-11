<div align=center>
<img src='https://user-images.githubusercontent.com/25563773/171997432-ff332de2-f85d-44e2-87d1-67247f73df9a.png' width="15%" />
</div>
<h2 align="center">记账喵后端</h2>
<p align="center">Accountant Meow Backend</p>

[![Java CI with Maven](https://github.com/lingluo-hub/accountant-meow-backend/actions/workflows/maven.yml/badge.svg)](https://github.com/lingluo-hub/accountant-meow-backend/actions/workflows/maven.yml)
![Spring Version](https://img.shields.io/badge/spring%20boot-2.7.0-green)

## 简介

采用 Spring Boot + MyBatis 核心技术栈，为记账喵安卓 App 开发的服务端, 遵循 [RESTful API](https://restfulapi.net/) 开发。

## 快速开始

### 环境准备

- JDK 11 (可前往 https://www.azul.com/downloads/?version=java-11-lts&package=jdk 下载对应操作系统的 Java JDK, 推荐版本 >=11)
- Maven (**可选** 可前往 https://maven.apache.org/ 获取最新版)
- Docker (**可选** 可前往 https://www.docker.com/get-started/ 获取 对应操作系统的 Docker)

### 环境变量说明

| 变量名          | 说明                        |
|:-------------|:--------------------------|
| USER_NAME    | Spring Boot Security 用户名  |
| USER_PSWD    | Spring Boot Security 用户密码 |
| DB_URL       | PostgreSQL数据库地址           |
| DB_USER_NAME | PostgreSQL数据库用户名          |
| DB_USER_PSWD | PostgreSQL数据库用户密码         |


### 构建

1. 本地环境 Maven 构建
```shell
git clone https://github.com/lingluo-hub/accountant-meow-backend.git
cd accountant-meow-backend
mvn -B package --file pom.xml -DskipTests
```

2. 使用 mvnw 构建
  ```shell
  ./mvnw -B package --file pom.xml -DskipTests
  ```

在 `target` 下可看到 `.jar` 的文件生成。

### 运行

1. Java 环境运行

  ```shell
  cd target
  mv accountant-meow-backend-*.jar app.jar
  java -jar app.jar --spring.security.user.name=${USER_NAME} --spring.security.user.password=${USER_PSWD} \
                  --spring.datasource.username=${DB_USER_NAME} --spring.datasource.password=${DB_USER_PSWD} \
                  --spring.datasource.url=${DB_URL}
  ```

2. Docker 运行
   新建文件 `.env` (环境变量配置文件), 填入以下内容:

  ```
  USER_NAME=<Spring Boot Security 用户名>
  USER_PSWD=<Spring Boot Security 用户密码>
  DB_URL=<PostgreSQL数据库地址>
  DB_USER_NAME=<PostgreSQL数据库用户名>
  DB_USER_PSWD=<PostgreSQL数据库用户密码>
  ```

然后运行:

  ```shell
  docker run --env-file .env adaclosure/acmw-backend:latest
  ```

本应用端口 `8080`，无图形化界面。

## 开发指引
### 开发环境

- JDK 11 (可前往 https://www.azul.com/downloads/?version=java-11-lts&package=jdk 下载对应操作系统的 Java JDK, 推荐版本 >=11)
- Maven (可前往 https://maven.apache.org/ 获取最新版)
- Docker (**可选** 可前往 https://www.docker.com/get-started/ 获取 对应操作系统的 Docker)
- PostgreSQL (本项目使用的后端数据库，开放端口 5432)
- IntelliJ IDEA (颇受欢迎的 Java 开发 IDE)

### 数据库初始化

**推荐** 开发环境下使用 docker 开启 PostgreSQL 数据库: 
```shell
docker run -v postgres-data:/var/lib/postgresql/data --name postgresql -e POSTGRES_PASSWORD=<数据库postgres用户密码> -p 5432:5432 -d postgres
```
创建数据库 `accountant_meow`, 本项目自带的 [flyway](https://flywaydb.org/) 会在启动时自动化此数据库。

**注意** 如使用同一数据库环境，再次运行时请将文件 `src/main/resources/application-dev.yml` 中的 `flyway:enabled:` 中 `true` 改为 `false`

接口文档：[APIfox 在线文档](https://www.apifox.cn/apidoc/shared-ea01e1d8-803d-4828-988e-540fd0a572e9)
