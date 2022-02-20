package com.wecode.bookstore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

//@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {
    @Bean
    public Docket bookStoreApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wecode.bookstore.controller"))
                .paths(regex("/.*"))
                .build()
                .apiInfo(apiMetaData());
    }

    private ApiInfo apiMetaData() {
        return new ApiInfo(
                "Book Store REST API",
                "All api's for the book store application",
                "1.0",
                "term and condition url",
                new Contact("BookStore Admin",
                        "https://book-store-web.herokuapp.com",
                        "bookstore@gmial.com"
                ),
                "book store licence",
                "licenase url",
                Collections.emptyList()
        );
    }

}