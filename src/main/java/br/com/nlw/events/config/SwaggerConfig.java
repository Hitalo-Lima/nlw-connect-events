package br.com.nlw.events.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        .info(new Info()
        .title("Events NLW")
        .version("1.0")
        .description("API de gerenciamento de eventos")
        .contact(new Contact()
        .name("Hítalo Lima")
        .email("hitalolima173@gmail.com")
        .url("github.com/hitalo-lima")));
    }
}
