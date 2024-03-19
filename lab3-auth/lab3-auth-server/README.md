# Web Server for lab3-auth

## Standalone

Usage:

```shell
Usage: lab3-auth-server.jar [options]
  Options:
  * -db_host
      Database host (example: localhost)
  * -db_name
      Database name (example: tws_db)
  * -db_password
      Database password
  * -db_port
      Database port (example: 5432)
  * -db_username
      Database username
    -h, -help

    -host
      Server URL base
      Default: http://localhost
    -port
      Server URL port
      Default: 8080
```

Example:

```shell
java -jar lab3-auth-server.jar \
    -host http://localhost -port 9595 \
    -db_host localhost -db_port 5439 -db_name tws_db -db_username postgres -db_password admin
```

java -jar lab2-server.jar -host http://localhost -port 9595 -db_host localhost -db_port 5439 -db_name tws_db -db_username postgres -db_password admin

## WAR package

You can deploy `WAR` package to you application server

Tested on `WildFly 27`

### Use maven install and deploy goal

1. Create `configuration.properties` file in project root using `example.configuration.properties`
1. Update database and wildfly properties
1. Execute `mvn clean install`

### Deploy manually

1. Deploy postgres database driver to your application server (check root `pom.xml` for version)
1. Add database properties

#### Configure database

There are 3 possible ways

##### Use ENV variables

```shell
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

##### Use system properties

Same names as env vars

##### Configure JNDI datasource

1. Create datasource in application server
1. Set `DB_JNDI_NAME` ENV or System property
