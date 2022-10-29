package com.rmn.toolkit.mediastorage.command.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                        .in(SecurityScheme.In.HEADER)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"))
                )
                .info(new Info().title("Media Storage Command API"));
    }
}