package com.klef.fsad.sdp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;

@Configuration
public class SwaggerConfig 
{
    @Bean
    public OpenAPI customOpenAPI() 
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Tourism Homestay API")
                        .version("1.0")
                        .description("Backend APIs for Tourism Homestay Platform")
                        .contact(new Contact()
                                .name("KL University Project")
                                .email("support@tourism.com"))
                        .license(new License()
                                .name("Open Source")
                                .url("https://opensource.org"))
                );
    }
}