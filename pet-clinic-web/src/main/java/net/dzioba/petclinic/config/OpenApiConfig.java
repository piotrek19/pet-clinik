package net.dzioba.petclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class OpenApiConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("net.dzioba.petclinic.api.v1.controllers"))
                    .paths(PathSelectors.any())
                    .build()
                .pathMapping("/")
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        return new ApiInfo(
                "Pet Clinic",
                "Pet Clinic - web app",
                "1.0",
                "",
                new Contact("Piotr Dzioba", "", "dziobap@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }





}
