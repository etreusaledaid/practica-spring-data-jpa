package com.ejemplo.java.pizzeria.web.controller;

import com.ejemplo.java.pizzeria.persistence.entity.UserEntity;
import com.ejemplo.java.pizzeria.persistence.repository.UserRepository;
import com.ejemplo.java.pizzeria.service.dto.LoginDto;
import com.ejemplo.java.pizzeria.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Autowired
    public UsersController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/usuarios")
    //El tipo de dato que retornara sera un HashMap<String, String>
    public ResponseEntity<HashMap<String, String>> getAll(@RequestBody LoginDto loginDto){
        //Se creo un UsernamePasswordAuthenticationToken a partir del usuario y contraseña que nos llego en loginDto
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        //Se creo un objeto de autenticación
        Authentication authentication = this.authenticationManager.authenticate(login);

        System.out.println("--------------------------");
        System.out.println(authentication.isAuthenticated()); //TRUE
        System.out.println("--------------------------");
        System.out.println(authentication.getPrincipal());
        System.out.println("--------------------------");

        //Se crea un Json Web Token que se añadira a la solicitud
        String jwt = this.jwtUtil.create(loginDto.getUsername());

        UserEntity userEntity = this.userRepository.findById(loginDto.getUsername()).orElseThrow(()->new UsernameNotFoundException("User "+loginDto.getUsername()+" not found"));

        System.out.println(userEntity);
        System.out.println("--------------------------");
        System.out.println(loginDto);
        System.out.println("--------------------------");

        //Aqui creo el HashMap<String, String>
        HashMap<String, String> map = new HashMap<>();
        map.put("username", userEntity.getUsername());
        map.put("email", userEntity.getEmail());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).body(map);
    }

}
