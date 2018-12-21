package com.sashaboss.xmltoyaml.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.xmldeser.ArrayInferringUntypedObjectDeserializer;
import com.google.xmldeser.RootSniffingXMLStreamReader;

/**
 * Provides functionality for XML to YAML conversion
 */
@Service
public class MyService {

    private final XmlMapper xmlMapper;

    private final YAMLMapper yamlMapper;

    /**
     * Creates a new service with instances of XML and YAML mappers
     * provided by Jackson
     */
    public MyService() {
        // create jackson xml mapper receiving object
        xmlMapper = new XmlMapper();
        Module module = new SimpleModule().addDeserializer(
                Object.class, new ArrayInferringUntypedObjectDeserializer()
        );
        xmlMapper.registerModule(module);
        // create yaml mapper
        yamlMapper = new YAMLMapper();
    }

    /**
     * Converts XML string into element map
     *
     * @param sXml - XML string
     * @return element map
     * @throws IOException on i/o problem
     * @throws XMLStreamException on input stream problem
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> xmlToMap(String sXml) throws IOException, XMLStreamException {
        InputStream inputStream = new ByteArrayInputStream(sXml.getBytes(StandardCharsets.UTF_8));

        // create DinoCheisa XML deserializer
        RootSniffingXMLStreamReader reader = new RootSniffingXMLStreamReader(
                XMLInputFactory.newFactory().createXMLStreamReader(inputStream));

        Map<String, Object> content = xmlMapper.readValue(reader, Map.class);
        Map<String, Object> map = new HashMap<>();
        map.put(reader.getLocalNameForRootElement(), content);
        return map;
    }

    /**
     * Converts element map to YAML string
     *
     * @param map - map to be converted
     * @return YAML string
     * @throws IOException on i/o problem
     */
    public String mapToYaml(Map<String, Object> map) throws IOException {
        StringWriter stringWriter = new StringWriter();

        // write map as YAML
        yamlMapper.writeValue(stringWriter, map);
        String sYaml = stringWriter.toString();

        // remove unnecessary dashes
        sYaml = sYaml.substring(sYaml.indexOf("---") + 4);
        return sYaml;
    }

}
