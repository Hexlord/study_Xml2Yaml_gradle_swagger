package com.sashaboss.xmltoyaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.sashaboss.xmltoyaml.model.MyService;

public class ConverterServiceTest {

    private final MyService service = new MyService();

    private Map<String, Object> test1Map;
    private Map<String, Object> test2Map;

    private final ClassLoader loader = getClass().getClassLoader();

    @Before
    public void init() {
        test1Map = new HashMap<>();
        Map<String, Object> test1 = new HashMap<String, Object>();
        test1.put("author", "Alexander");
        test1.put("secrecy", "Absolutely secret");
        test1.put("access", "Denied");
        test1Map.put("document", test1);

        test2Map = new HashMap<>();
        Map<String, Object> test2 = new HashMap<String, Object>();
        test2.put("target", "Yasos Biba");
        test2.put("action", "\uD83D\uDD2A");
        test2.put("contractor", "Unknown");
        test2.put("payment", "$15'000'000 USA");
        test2Map.put("contract", test2);
    }

    private String stringFromResource(String sName) throws IOException {
        InputStream file = loader.getResourceAsStream(sName);
        return IOUtils.toString(file, StandardCharsets.UTF_8);
    }


    private Map<String, Object> mapFromResourceXml(String sName) throws IOException, XMLStreamException {
        String sXml = stringFromResource(sName);
        return service.xmlToMap(sXml);
    }

    @Test
    public void testMap1() throws IOException, XMLStreamException {
        Assert.assertEquals("Unexpected test1.xml element map",
                test1Map,
                mapFromResourceXml("test1.xml"));
    }

    @Test
    public void testMap2() throws IOException, XMLStreamException {
        Assert.assertEquals("Unexpected test2.xml element map",
                test2Map,
                mapFromResourceXml("test2.xml"));
    }

    @Test
    public void testYaml1() throws IOException, XMLStreamException {
        Assert.assertEquals("Test english text",
                stringFromResource("test1.yaml"),
                service.mapToYaml(mapFromResourceXml("test1.xml")));
    }

    @Test
    public void testYaml2() throws IOException, XMLStreamException {
        Assert.assertEquals("Test UTF-8 Emoji",
                stringFromResource("test2.yaml"),
                service.mapToYaml(mapFromResourceXml("test2.xml")));
    }

    @Test(expected = JsonParseException.class)
    public void testException() throws IOException, XMLStreamException {
        service.xmlToMap(stringFromResource("malformed.xml"));
    }

}
