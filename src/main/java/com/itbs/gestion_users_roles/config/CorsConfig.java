package com.itbs.gestion_users_roles.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/api/**")
    //             .allowedOrigins("http://localhost:4200", "http://localhost:3000","http://34.38.180.201")
    //             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    //             .allowedHeaders("*")
    //             .allowCredentials(true)
    //             .maxAge(3600);
    // }
}
