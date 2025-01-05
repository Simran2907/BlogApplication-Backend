package com.springboot.blog.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
	public static final String SPRING_SECURITY_SCHEME_NAME = "JWT";

    @Bean
    public OpenAPI customOpenAPI() {
    	
        return new OpenAPI()
                .info(new Info()
                        .title("Blog Application - API Documentation") // Set the header title here
                        .version("1.0")
                        .contact(new Contact().name("Simran Ghotra")
                        		.email("sghotra297@gmail.com")
                        		.url("simu.www.in"))
                        .description("Description of the API"))
                .addSecurityItem(new SecurityRequirement().addList(SPRING_SECURITY_SCHEME_NAME))
                .components(new Components()
                		.addSecuritySchemes(SPRING_SECURITY_SCHEME_NAME, new SecurityScheme()
                				.type(SecurityScheme.Type.HTTP)
                				.scheme("bearer")
                				.bearerFormat(SPRING_SECURITY_SCHEME_NAME)
                				)
                		);
    }
}
