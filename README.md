# itmo-web-services-technologies

Laboratory works of Web Services Technologies. Software Engineering Course ITMO magistracy 2-year

## Used techs

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [JUnit 5](https://junit.org/junit5/)
- [Maven 3](https://maven.apache.org/)
- Docker (for database and server deployment)

## Dev deps

- [pre-commit](https://pre-commit.com/)
- [python 3.9+](https://www.python.org/downloads/release/python-3913/) (for pre-commit setup)

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

#### Build

```shell
mvn clean install
```

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
