package com.equipo_1.SkyShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") 
                        .allowedOrigins("http://ec2-54-175-245-83.compute-1.amazonaws.com") // dirección dns publica de instancia ec2
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  
                        .allowedHeaders("*")  
                        .allowCredentials(true); 
            }
        };
    }
}