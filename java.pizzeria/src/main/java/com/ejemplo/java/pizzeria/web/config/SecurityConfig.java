package com.ejemplo.java.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        /*http.authorizeHttpRequests().anyRequest().permitAll();*/ //Permitir todas las conexiones
        /*Deshabilitar protección CSRF*/
        //https://docs.spring.io/spring-security/reference/features/exploits/csrf.html#csrf-when
        /*Creando la configuración de CORS*/
        http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
}
