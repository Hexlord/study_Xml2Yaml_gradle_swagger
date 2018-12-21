# XMLtoYAML

## About
This web service converts XML input to YAML output
It is implemented using Java and Spring Boot framework

API description is provided through swagger and is available on `/swagger-ui.html`.

## Example

The service has controller handling POST request to `/convert` with body containing XML:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<document>
    <author>Alexander</author>
    <secrecy>Absolutely secret</secrecy>
    <access>Denied</access>
</document>
```

and sends response in YAML format matching the request:
```yaml
document:
  author: "Alexander"
  secrecy: "Absolutely secret"
  access: "Denied"

```

## Usage 

1. Create docker image (make sure you have root/administrator privileges)

`gradlew docker`

2. Run it with the script

`run.cmd`


