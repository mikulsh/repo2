package com.operations.crud.configs.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    Parameter authHead = new ParameterBuilder()
            .parameterType("header")
            .name("Authorization")
            .modelRef(new ModelRef("String"))
            .build();

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    @Bean
    public Docket employeeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Employee")
                .ignoredParameterTypes(Sort.class, Page.class)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.operations.crud.controller.employee"))
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(Collections.singletonList(authHead));
    }

    @Bean
    public Docket departmentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Department")
                .ignoredParameterTypes(Sort.class, Page.class)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.operations.crud.controller.department"))
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(Collections.singletonList(authHead));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Employe-Department CRUD operation -  REST APIs")
                .description("Spring boot REST application")
                .contact(new Contact("Devashish", "https://unthinkable.co", "devashish@unthinkable.co"))
                .version("1.0")
                .build();
    }

}