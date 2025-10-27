package org.elis.horizon.horizonrent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI horizonRentOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Horizon Rent API")
                .description("REST API for Horizon Rent property management system")
                .version("v1.0")
                .contact(new Contact()
                    .name("Horizon Rent Support")
                    .email("support@horizonrent.com")));
    }
}
