# XML to YAML Converter

Very powerful and easy to use XML to YAML converter service. It's written in Java 10 using Spring Boot framework.

## Example

The service has controller handling POST request to `/convert` with body containing XML:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<note>
    <to>Tove</to>
    <from>Jani</from>
    <heading>Reminder</heading>
    <body>Don't forget me this weekend!</body>
</note>
```

and sends response in YAML format matching the request:
```yaml
note:
  to: "Tove"
  from: "Jani"
  heading: "Reminder"
  body: "Don't forget me this weekend!"
```

## Usage 

To create Docker image run Gradle task:

`$ ./gradlew docker`

After that Docker container can be run with optional port parameter (default is 8080):

`$ ./run.sh <PORT>`

Swagger API description is available on `/swagger-ui.html` and `/v2/api-docs`.
