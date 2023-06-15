package com.ejemplo.java.pizzeria.service;

import com.ejemplo.java.pizzeria.persistence.entity.PizzaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service/**Es un Bean de spring*/
public class PizzaService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired/**Se encarga de la inyección de dependencias*/
    public PizzaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*Este metodo retorna lo que llama la clase PizzaEntity*/
    public List<PizzaEntity> getAll(){
        return this.jdbcTemplate.query("Select * from pizza", new BeanPropertyRowMapper<>(PizzaEntity.class)); /*Permite crear consultas sql y convertilas en clases java*/
    }
}
