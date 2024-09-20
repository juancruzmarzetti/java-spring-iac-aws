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
                registry.addMapping("/**")  // Permite todas las rutas
                        .allowedOrigins("http://ec2-34-239-185-158.compute-1.amazonaws.com")  // Permite solo tu frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  // Métodos permitidos
                        .allowedHeaders("*")  // Permite todos los headers
                        .allowCredentials(true);  // Permite credenciales como cookies, authorization headers
            }
        };
    }
}