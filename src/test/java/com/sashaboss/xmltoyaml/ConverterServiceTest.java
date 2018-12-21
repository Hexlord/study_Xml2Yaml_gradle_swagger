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

/**
 * Provides tests for MyService functionality
 */
public class ConverterServiceTest {

    private final MyService service = new MyService();

    private Map<String, Object> test1Map;
    private Map<String, Object> test2Map;

    private final ClassLoader loader = getClass().getClassLoader();

    /**
     * Initializes element maps for testing purposes
     */
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

    /**
     * Opens resource file and extracts its content
     * in form of UTF-8 encoded string
     *
     * @param sName - file name to be read
     * @return file content as unicode string
     * @throws IOException on i/o problem
     */
    private String stringFromResource(String sName) throws IOException {
        InputStream file = loader.getResourceAsStream(sName);
        return IOUtils.toString(file, StandardCharsets.UTF_8);
    }

    /**
     * Opens resource file with XML and
     * forms element map from it
     *
     * @param sName - file name to be read
     * @return XML as element map
     * @throws IOException on i/o problem
     * @throws XMLStreamException on xml input problem
     */
    private Map<String, Object> mapFromResourceXml(String sName) throws IOException, XMLStreamException {
        String sXml = stringFromResource(sName);
        return service.xmlToMap(sXml);
    }

    /**
     * Checks whether XML to element map conversion
     * behaves as expected for test1.xml
     *
     * @throws IOException on i/o problem
     * @throws XMLStreamException on xml input problem
     */
    @Test
    public void testMap1() throws IOException, XMLStreamException {
        Assert.assertEquals("Unexpected test1.xml element map",
                test1Map,
                mapFromResourceXml("test1.xml"));
    }

    /**
     * Checks whether XML to element map conversion
     * behaves as expected for test2.xml which contains UTF-8 emojis
     *
     * @throws IOException on i/o problem
     * @throws XMLStreamException on xml input problem
     */
    @Test
    public void testMap2() throws IOException, XMLStreamException {
        Assert.assertEquals("Unexpected test2.xml element map",
                test2Map,
                mapFromResourceXml("test2.xml"));
    }

    /**
     * Checks whether XML to YAML conversion
     * behaves as expected for test1.xml
     *
     * @throws IOException on i/o problem
     * @throws XMLStreamException on xml input problem
     */
    @Test
    public void testYaml1() throws IOException, XMLStreamException {
        Assert.assertEquals("Test english text",
                stringFromResource("test1.yaml"),
                service.mapToYaml(mapFromResourceXml("test1.xml")));
    }

    /**
     * Checks whether XML to YAML conversion
     * behaves as expected for test2.xml which contains UTF-8 emojis
     *
     * @throws IOException on i/o problem
     * @throws XMLStreamException on xml input problem
     */
    @Test
    public void testYaml2() throws IOException, XMLStreamException {
        Assert.assertEquals("Test UTF-8 Emoji",
                stringFromResource("test2.yaml"),
                service.mapToYaml(mapFromResourceXml("test2.xml")));
    }

    /**
     * Checks whether XML to YAML conversion
     * behaves as expected for malformed XML file
     *
     * @throws IOException on i/o problem
     * @throws XMLStreamException on xml input problem
     */
    @Test(expected = JsonParseException.class)
    public void testException() throws IOException, XMLStreamException {
        service.xmlToMap(stringFromResource("malformed.xml"));
    }

}
