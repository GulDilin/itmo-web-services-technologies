# CLI client for lab4-server

## Usage

```shell
Usage: lab4-client.jar [options]
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
Usage: lab4-client.jar [options]
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
    -limit
      Results limit
    -meters-above-sea-level
      Meters above sea level for City
    -name
      City name
    -offset
      Results offset
    -population
      City population value
    -population-density
      City population density
  * -url
      Server URL base (example: http://localhost:8080)
```

Example

```shell
java -jar .\lab4-client\target\lab4-client.jar \
    -url http://localhost:9596/lab4-server \
    -c find \
    -area 98 \
    -limit 1 \
    -offset 10
```

## Use create command

```shell
Usage: lab4-client.jar [options]
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
java -jar lab4-client.jar \
    -url http://localhost:9596/lab4-server \
    -c create \
    -area 99 -car-code 99 -meters-above-sea-level 10 -name Novgorod -population 20 -population-density 20
```

## Use update command

```shell
Usage: lab4-client.jar [options]
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
java -jar lab4-client.jar \
    -url http://localhost:9596/lab4-server \
    -c update \
    -id 1 \
    -area 99 -car-code 99 -meters-above-sea-level 10 -name Novgorod -population 20 -population-density 20
```

## Use patch command

```shell
Usage: lab4-client.jar [options]
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
java -jar lab4-client.jar \
    -url http://localhost:9596/lab4-server \
    -c patch \
    -id 1 \
    -area 99
```

## Use delete command

```shell
Usage: lab4-client.jar [options]
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
java -jar lab4-client.jar \
    -url http://localhost:9596/lab4-server \
    -c delete \
    -id 1
```
