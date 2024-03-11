# CLI client for lab2-server

## Usage

```shell
Usage: lab7-client.jar [options]
  Options:
  * -c, -command
      Command name
      Possible Values: [find, create, update, patch, delete]
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -juddi_host
      jUDDI URL hostname (example: localhost)
  * -juddi_port
      jUDDI port (example: 8080)
```

## Usage find command

```shell
Usage: lab7-client.jar [options]
  Options:
  * -c, -command
      Command name
      Possible Values: [find, create, update, patch, delete]
    -filter, -f
      Filters for read command in format field:operation:value (area:=:1)
      Default: []
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -juddi_host
      jUDDI URL hostname (example: localhost)
  * -juddi_port
      jUDDI port (example: 8080)
    -limit
      Results limit
    -offset
      Results offset
```

Example find

```shell
java --add-opens java.base/java.net=ALL-UNNAMED \
    -jar lab7-client.jar \
    -juddi_host localhost -juddi_port 8099
    -c find
    -f area:=:78 -f carCode:=:198 \
    -limit 10 \
    -offset 5
```

## Usage create command

```shell
Usage: lab7-client.jar [options]
  Options:
  * -area
      City area number
  * -car-code
      City car code number
  * -c, -command
      Command name
      Possible Values: [find, create, update, patch, delete]
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -juddi_host
      jUDDI URL hostname (example: localhost)
  * -juddi_port
      jUDDI port (example: 8080)
  * -meters-above-sea-level
      Meters above sea level for City
  * -name
      City name
  * -population
      City population value
  * -population-density
      City population density
```

Example create

```shell
java --add-opens java.base/java.net=ALL-UNNAMED \
    -jar lab7-client.jar \
    -juddi_host localhost -juddi_port 8099 \
    -c create \
    -area 99 -car-code 99 -meters-above-sea-level 10 -name Novgorod -population 20 -population-density 20
```

## Usage update command

```shell
Usage: lab7-client.jar [options]
  Options:
  * -area
      City area number
  * -car-code
      City car code number
  * -c, -command
      Command name
      Possible Values: [find, create, update, patch, delete]
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -id
      City id
  * -juddi_host
      jUDDI URL hostname (example: localhost)
  * -juddi_port
      jUDDI port (example: 8080)
  * -meters-above-sea-level
      Meters above sea level for City
  * -name
      City name
  * -population
      City population value
  * -population-density
      City population density

```

Example update (provide all fields)

```shell
java --add-opens java.base/java.net=ALL-UNNAMED \
    -jar lab7-client.jar \
    -juddi_host localhost -juddi_port 8099 \
    -c update \
    -id 1 \
    -area 99 -car-code 99 -meters-above-sea-level 10 -name Novgorod -population 20 -population-density 20
```

## Usage patch command

```shell
Usage: lab7-client.jar [options]
  Options:
    -area
      City area number
    -car-code
      City car code number
  * -c, -command
      Command name
      Possible Values: [find, create, update, patch, delete]
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -id
      City id
  * -juddi_host
      jUDDI URL hostname (example: localhost)
  * -juddi_port
      jUDDI port (example: 8080)
    -meters-above-sea-level
      Meters above sea level for City
    -name
      City name
    -population
      City population value
    -population-density
      City population density
```

Example patch (provide fields you want to change)

```shell
java --add-opens java.base/java.net=ALL-UNNAMED \
    -jar lab7-client.jar \
    -juddi_host localhost -juddi_port 8099 \
    -c patch \
    -id 1 \
    -area 99
```

## Usage delete command

```shell
Usage: lab7-client.jar [options]
  Options:
  * -c, -command
      Command name
      Possible Values: [find, create, update, patch, delete]
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -id
      City id
  * -juddi_host
      jUDDI URL hostname (example: localhost)
  * -juddi_port
      jUDDI port (example: 8080)
```

Example delete

```shell
java --add-opens java.base/java.net=ALL-UNNAMED \
    -jar lab7-client.jar \
    -juddi_host localhost -juddi_port 8099 \
    -c delete \
    -id 1
```
