package com.sashaboss.xmltoyaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Provides Spring entry point for xml_to_yaml
 */
@SpringBootApplication
public class XmlToYaml {
    /**
     * Launches Spring application while passing command line arguments to it
     *
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(XmlToYaml.class, args);
    }
}
