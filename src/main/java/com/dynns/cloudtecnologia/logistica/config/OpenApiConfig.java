package com.dynns.cloudtecnologia.logistica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Máxima Logística")
                        .description("API Para manipular Clientes")
                        .contact(new Contact()
                                .name("Thiago Junior")
                                .email("thi4go19@gmail.com")
                                .url("https://www.linkedin.com/in/thiago-melo-84246a149")
                        )
                        .version("16/08/2023"));

    }

}
