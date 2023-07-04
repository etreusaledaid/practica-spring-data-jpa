package com.ejemplo.java.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));        //Que direccion permitir
        corsConfiguration.setAllowedOrigins(Arrays.asList("GET","POST","PUT","DELETE"));    //Que metodos permitir
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));                             //Permitir todos los encabezados

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); //El simbolo /** indica que es para todos los controladores

        return source;
    }
}
