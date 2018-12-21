package com.sashaboss.xmltoyaml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Set-ups Swagger 2
 * for request handling
 */
@Configuration
@EnableSwagger2
public class MyConfig {
    /**
     * Set-up Swagger 2 documentation
     *
     * @return builder which is intended to be the primary interface into the Springfox framework
     * that provides sensible defaults and convenience methods for configuration.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sashaboss.xmltoyaml.servlet"))
                .paths(PathSelectors.any())
                .build();
    }
}
