# CLI client for lab2-server

## Usage

```shell
Usage: lab2-client.jar [options]
  Options:
  * -c, -command
      Command name
      Possible Values: [find]
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -url
      Server URL base (example: http://localhost:8080)
```

## Use find command

```shell
Usage: lab2-client.jar [options]
  Options:
  * -c, -command
      Command name
      Possible Values: [find]
    -filter, -f
      Filters for read command in format field:operation:value (area:=:1)
      Default: []
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
    -limit
      Results limit
    -offset
      Results offset
  * -url
      Server URL base (example: http://localhost:8080)
```

Example

```shell
java -jar lab2-client.jar \
    -url http://localhost:8080 \
    -c find \
    -f area:=:78 -f carCode:=:198 \
    -limit 10 \
    -offset 10
```

## Use create command

```shell
Usage: lab2-client.jar [options]
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
  * -meters-above-sea-level
      Meters above sea level for City
  * -name
      City name
  * -population
      City population value
  * -population-density
      City population density
  * -url
      Server URL base (example: http://localhost:8080)
```

Example

```shell
java -jar lab2-client.jar \
    -url http://localhost:9596/lab2-server \
    -c create \
    -area 99 -car-code 99 -meters-above-sea-level 10 -name Novgorod -population 20 -population-density 20
```

## Use update command

```shell
Usage: lab2-client.jar [options]
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
  * -meters-above-sea-level
      Meters above sea level for City
  * -name
      City name
  * -population
      City population value
  * -population-density
      City population density
  * -url
      Server URL base (example: http://localhost:8080)
```

Example

```shell
java -jar lab2-client.jar \
    -url http://localhost:9596/lab2-server \
    -c update \
    -id 1 \
    -area 99 -car-code 99 -meters-above-sea-level 10 -name Novgorod -population 20 -population-density 20
```

## Use patch command

```shell
Usage: lab2-client.jar [options]
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
    -meters-above-sea-level
      Meters above sea level for City
    -name
      City name
    -population
      City population value
    -population-density
      City population density
  * -url
      Server URL base (example: http://localhost:8080)
```

Example

```shell
java -jar lab2-client.jar \
    -url http://localhost:9596/lab2-server \
    -c patch \
    -id 1 \
    -area 99
```

## Use delete command

```shell
Usage: lab2-client.jar [options]
  Options:
  * -c, -command
      Command name
      Possible Values: [find, create, update, patch, delete]
    -help, -h
      Show help message (also can be used with -c argument to show help
      message for command)
  * -id
      City id
  * -url
      Server URL base (example: http://localhost:8080)
```

Example

```shell
java -jar lab2-client.jar \
    -url http://localhost:9596/lab2-server \
    -c delete \
    -id 1
```
