package com.ejemplo.java.pizzeria.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component /*Component funciona para que se pueda inyectar al ciclo de vida de Spring*/
public class JwtUtil {
    /*Creando la clave secreta*/
    private static String SECRET_KEY = "j4v4_p1zz4";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String create(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer("java-pizza")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(ALGORITHM);
    }

    /*Creación de servicio para validar un JWT*/
    public boolean isValid(String jwt){
        try {
            JWT.require(ALGORITHM) //El ALGORITHM es el que creamos
                    .build()
                    .verify(jwt);
            return true;
        } catch(JWTVerificationException e){
            return false;
        }
    }

    //Metodo para obtener el nombre del usuario a quien pertenece el token
    public String getUsername(String jwt){
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                .getSubject();
    }
}
