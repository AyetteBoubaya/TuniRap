package com.TuniRap.catalogueManagement.configurations;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class swaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                                .title("Catalogue Management API")
                                .version("1.0")
                                .description("Microservices pour la gestion du  catalogue des artistes et albums .")
                );
    }

}
