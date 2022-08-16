<div align="center"> <img src="https://user-images.githubusercontent.com/25563773/171997432-ff332de2-f85d-44e2-87d1-67247f73df9a.png" width="15%">
</div>
<h2 align="center">Accountant Meow Backend</h2>
<p align="center">Accountant Meow Backend</p>

[](https://www.oscs1024.com/project/lingluo-hub/accountant-meow-backend?ref=badge_small)![OSCS Status](https://www.oscs1024.com/platform/badge/lingluo-hub/accountant-meow-backend.svg?size=small) [](https://github.com/lingluo-hub/accountant-meow-backend/actions/workflows/maven.yml)![Java CI with Maven](https://github.com/lingluo-hub/accountant-meow-backend/actions/workflows/maven.yml/badge.svg) ![Spring Version](https://img.shields.io/badge/spring%20boot-2.7.0-green) [](https://codecov.io/gh/lingluo-hub/accountant-meow-backend)![codecov](https://codecov.io/gh/lingluo-hub/accountant-meow-backend/branch/main/graph/badge.svg?token=FgwEZXPtOM)

[中文](./README.cn.md) | English

## Introduction

Using Spring Boot + MyBatis core technology stack, the server developed for the Accountant Meow Android App follows the [RESTful API](https://restfulapi.net/) development.
Use the smart-doc api interface document generator.

## Quick Start

### Environmental preparation

- JDK 11 (Go to https://www.azul.com/downloads/?version=java-11-lts&amp;package=jdk to download the Java JDK of the corresponding operating system, the recommended version&gt;=11)
- Maven ( **optional** , go to https://maven.apache.org/ to get the latest version)
- Docker ( **optional** , go to https://www.docker.com/get-started/ to get Docker for the corresponding operating system)

### Description of environment variables

| Variable Name | Description                                    |
|:--------------|:-----------------------------------------------|
| USER_NAME     | Spring Boot Security user name                 |
| USER_PSWD     | Spring Boot Security user password             |
| DB_URL        | PostgreSQL database address, default:localhost |
| DB_USER_NAME  | PostgreSQL database username, default:postgres |
| DB_USER_PSWD  | PostgreSQL database user password              |
| DB_PORT       | PostgreSQL database port, default:5432         |
| REDIS_URL     | Redis database address, default:localhost      |
| REDIS_PORT    | Redis database port, default:6379              |

### Build

1. Local environment Maven build

```shell
git clone https://github.com/lingluo-hub/accountant-meow-backend.git
cd accountant-meow-backend
mvn -B package --file pom.xml -DskipTests
```

2. build with mvnw

```shell
./mvnw -B package --file pom.xml -DskipTests
```

You can see the `.jar` file generation under the `target` .

3. *(Optional)* Generate API documentation

```shell
. /mvnw smart-doc:html
```

will generate html documentation site under ``src/main/resources/static/doc``. It can be viewed at `http://localhost/doc/api.html` after running.

See [smart-doc](https://smart-doc-group.github.io/) for details .

### Run

1. Java environment running

```shell
cd target
mv accountant-meow-backend-*.jar app.jar
java -jar app.jar
```

2. Docker runs the new file `.env` (environment variable configuration file) with the following content:

```
USER_NAME=<Spring Boot Security username>
USER_PSWD=<Spring Boot Security user password>
DB_URL=<PostgreSQL database url, default:localhost>
DB_USER_NAME=<PostgreSQL database username, default:postgres>
DB_USER_PSWD=<PostgreSQL database user password>
DB_PORT=<PostgreSQL database port, default:5432>
REDIS_URL=<Redis database address, default:localhost>
REDIS_PORT=<Redis database port, default:6379>
```

Then run:

latest release

```shell
docker run --env-file .env adaclosure/acmw-backend:1.4.2
```

Latest development version

```shell
docker run --env-file .env adaclosure/acmw-backend:latest
```

This application port `8080` , no graphical interface.

## Development Guidelines

### Development Environment

- JDK 11 (Go to https://www.azul.com/downloads/?version=java-11-lts&amp;package=jdk to download the Java JDK of the corresponding operating system, the recommended version&gt;=11)
- Maven (go to https://maven.apache.org/ to get the latest version)
- Docker ( **optional** , go to https://www.docker.com/get-started/ to get the Docker for the corresponding operating system)
- PostgreSQL (backend database used in this project, open port 5432)
- Redis (cache database used in this project, open port 6379)
- IntelliJ IDEA (popular IDE for Java development)

### Database initialization

It is **recommended** to use docker to start PostgreSQL and Redis database in development environment:

```shell
docker run \
  -v postgres-data:/var/lib/postgresql/data \
  --name postgresql \
  -e POSTGRES_PASSWORD=<postgres user password> \
  -e POSTGRES_DB=accountant_meow \
  -p 5432:5432 \
  -d postgres
```

```shell
docker run --name redis -p 6379:6379 -d redis redis-server --appendonly yes
```

When docker starts, the database `accountant_meow` has been created. The [flyway](https://flywaydb.org/) that comes with this project will automatically initialize the database, create tables and insert sample data at startup. Configuration file location: `src/main/resources/db/migration/V1.0__Init_DB.sql` and `src/main/resources/db/migration/V1.1__Init_Data.sql`

Interface Documentation: [APIfox Online Documentation](https://www.apifox.cn/apidoc/shared-ea01e1d8-803d-4828-988e-540fd0a572e9)
