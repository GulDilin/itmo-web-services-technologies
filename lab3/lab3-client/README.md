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
