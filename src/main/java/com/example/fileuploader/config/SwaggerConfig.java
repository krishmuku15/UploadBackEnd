package com.example.fileuploader.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("File Downloader API")
                        .description("Spring Boot + MongoDB API to upload files, store locally, and save metadata")
                        .version("1.0.0"));
    }
}
