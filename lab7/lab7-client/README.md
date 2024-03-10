# CLI client for lab2-server

## Usage

```shell
Usage: lab7-client.jar [options]
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
Usage: lab7-client.jar [options]
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
java --add-opens java.base/java.net=ALL-UNNAMED \
    -jar lab7-client.jar \
    -juddi_host localhost -juddi_port 8099
    -c find
    -f area:=:78 -f carCode:=:198 \
    -limit 10 \
    -offset 5
```
