package com.sashaboss.xmltoyaml.servlet;

import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.sashaboss.xmltoyaml.model.MyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

/**
 * Provides web controllers for the xml_to_yaml api
 */
@Api
@RestController
public class MySpringController {

    @Autowired
    private MyService service;

    /**
     * Controller for XML to YAML conversion requests
     *
     * @param sXml - input XML to be converted
     * @return YAML output when possible together with HTTP code
     * matching occurred exception or 200 if okay.
     */
    @ApiOperation(value = "This tool converts XML string to YAML string")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully converted XML to YAML"),
            @ApiResponse(code = 400, message = "Bad input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(
            value = "/convert",
            consumes = "application/xml",
            produces = "application/x-yaml"
    )
    public ResponseEntity<String> convert(
            @ApiParam(
                    name = "xml",
                    value = "source XML",
                    required = true,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/xml",
                                    value = "<example><somefield>some value</somefield></example>"
                            )
                    )
            )
            @RequestBody String sXml) {
        try {
            // parse XML as element map
            Map<String, Object> map = service.xmlToMap(sXml);
            // return YAML
            return new ResponseEntity<>(service.mapToYaml(map), HttpStatus.OK);
        } catch (JsonParseException | XMLStreamException e) {
            // something wrong with input
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
