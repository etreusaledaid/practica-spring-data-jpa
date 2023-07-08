package com.ejemplo.java.pizzeria.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
/*Control de métodos con Method Security*/
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    /*Aplicando filtro en la configuración*/
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /*------------------------------------*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        /*http.authorizeHttpRequests().anyRequest().permitAll();*/ //Permitir todas las conexiones

        /*Deshabilitar protección CSRF*/
        //https://docs.spring.io/spring-security/reference/features/exploits/csrf.html#csrf-when

        http
                /*Creando la configuración de CORS*/
                .csrf().disable()
                .cors().and()
                /*Aplicando un filtro en la configuración*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                /*---------------------------------------*/
                .authorizeHttpRequests()
                /*Creando un JWT cuando un usuario inicie sesión*/
                .requestMatchers("/api/auth/**").permitAll()
                /*Aplicando requestMatchers*/
                //.requestMatchers(HttpMethod.GET,"/api/pizzas/**").permitAll() //Los * representan los niveles de la url
                .requestMatchers(HttpMethod.GET,"/api/pizzas/**").hasAnyRole("ADMIN","CUSTOMER")
                .requestMatchers(HttpMethod.POST,"/api/pizzas/*").hasRole("ADMIN")
                /*Control de métodos con Method Security*/
                .requestMatchers("api/customers/**").hasAnyRole("ADMIN","CUSTOMER")
                //.requestMatchers(HttpMethod.PUT).denyAll()
                /*Aplicando authorities a los usuarios*/
                .requestMatchers("/api/orders/random").hasAuthority("random_order")
                /*Aplicando requestMatchers con roles*/
                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .requestMatchers("/api/orders/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic();
        /*Aplicando un filtro en la configuración*/
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        /*----------------------------------*/
        return http.build();
    }

    /*Creando un usuario para autenticar y dejar de usar el que proporciona el sistema por defecto*/
    /*@Bean
    public UserDetailsService memoryUsers(){
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails customer = User.builder()
                .username("customer")
                .password(passwordEncoder().encode("customer"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(admin,customer);
    }*/

    /*Creando un JWT cuando un usuario inicie sesión*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    /*--------------------------------------*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
