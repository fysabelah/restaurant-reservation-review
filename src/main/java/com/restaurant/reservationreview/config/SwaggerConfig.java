package com.restaurant.reservationreview.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Restaurante - Reserva e Avaliação")
                                .description("API para reservas e avaliação de restaurantes")
                                .version("1.0.0")
                );
    }
}