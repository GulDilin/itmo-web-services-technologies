# itmo-web-services-technologies

Laboratory works of Web Services Technologies. Software Engineering Course ITMO magistracy 2-year

Introduction to Java Web Services based on SOAP and REST

## Technology Stack

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [JUnit 5](https://junit.org/junit5/)
- [Maven 3](https://maven.apache.org/)
- [Flyway](https://flywaydb.org/) For database migrations
- [Weld](https://weld.cdi-spec.org/) For Provide CDI in Standalone application
- [Hibernate](https://hibernate.org/) Database ORM
- [Jakarta EE](https://jakarta.ee/) Jakarta EE 10 specifications
- [JAX-WS](https://jakarta.ee/specifications/xml-web-services/4.0/) Jakarta XML Web Services 4.0 for SOAP API
- [JAX-RS](https://jakarta.ee/specifications/restful-ws/3.1/) Jakarta RESTful Web Services 3.1
- [PostgreSQL](https://www.postgresql.org/) Database
- [jUDDI](https://juddi.apache.org/) Apache jUDDI service discovery

## Dev deps

- [pre-commit](https://pre-commit.com/)
- [python 3.9+](https://www.python.org/downloads/release/python-3913/) (for pre-commit setup)
- [Docker](https://www.docker.com/) (for database and server deployment)

### Some notes

> **_NOTE:_**  GitHub workflows are set up for that repo.
> To **bump** `major version` add `#major` string to commit message

> **_NOTE:_** To **bump** `minor version` add `#minor` string to commit message

> **_NOTE:_** To **bump** `third level version` add `#patch` string to commit message
>
> **_NOTE:_** To develop `lab1-client` you need to have deployed lab1 app

### Start database and application server with docker

```shell
docker compose -f deploy/local.docker-compose.yml up --build -d
```

### Setup dev env

1. Install Java 17 [\[download\]](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
1. Check path to java in `PATH` env variable (default is `C:\Program Files\Java\jdk-17\bin`)
1. Check `JAVA_HOME` env variable (default is `C:\Program Files\Java\jdk-17`)
1. Check `java -version`
1. Download Maven 3 [\[download\]](https://dlcdn.apache.org/maven/maven-3/)
1. Extract downloaded archive to target installation directory (for example `C:\Program Files\maven`)
1. Add maven `bin` directory to `PATH` env variable (for example `C:\Program Files\maven\bin`)
1. Check `mvn -version`

### Commands

#### Run environment

To run `database` and `WildFly` server you can use docker-compose file located in
`deploy/local.docker-compose.yml`

```shell
docker compose -f deploy/local/docker-compose.yml up --build -d
```

WildFly will start at port `9990` (admin)

```shell
Username: admin
Password: admin
```

#### Build

This command will build next artifacts:

- executable `JAR` package
- packaged `WAR` for deployment on application server

```shell
cd lab#
mvn clean package
```

#### Deploy to local wildfly server

1. Start docker compose environment

```shell
docker compose -f deploy/local/docker-compose.yml up --build -d
```

1. Copy `example.configuration.properties` to `configuration.properties`
1. Check and update values in `configuration.properties` if you want to use your wildfly or database
1. Package and deploy app

```shell
cd lab#
mvn clean install
```

This command will:

- package app
- deploy database driver
- attach datasource
- deploy WAR package

#### Prepare project report

#### Add database migrations

Project uses Flyway migrations. They are stored in /resources/db/migrations.
File naming format is `V{x}_{x}__{description}.sql`

So to add migration create new sql file and add commands.

#### Check codestyle

```shell
mvn clean checkstyle:check
```

#### Format codestyle

```shell
mvn clean spotless:apply
```

#### Check code formatting

```shell
mvn clean spotless:check
```

### Setup pre-commit

1. Install `python 3.9` [\[download\]](https://www.python.org/downloads/release/python-390/)
1. Check installation with command `python --version`
1. Add `pre-commit` dependency
   You can use global python dependencies, but we recommend to use virtual env
   Variant 1: virtual env (recommended)

```shell
python -m venv venv
./venv/Scripts/activate 	# for Windows
source venv/bin/activate 	# for *NIX
python -m pip install pre-commit==3.4.0
```

Variant 2: global

```shell
python -m pip install pre-commit==3.4.0
```

4. Install `pre-commit` hook

```shell
pre-commit install
```
