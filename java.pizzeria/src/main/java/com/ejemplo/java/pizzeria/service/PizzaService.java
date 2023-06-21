package com.ejemplo.java.pizzeria.service;

import com.ejemplo.java.pizzeria.persistence.entity.PizzaEntity;
import com.ejemplo.java.pizzeria.persistence.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service/**Es un Bean de spring*/
public class PizzaService {
    //private final JdbcTemplate jdbcTemplate;
    private final PizzaRepository pizzaRepository;

    @Autowired/**Se encarga de la inyección de dependencias*/
    /*public PizzaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    /*Este metodo retorna lo que llama la clase PizzaEntity*/
    public List<PizzaEntity> getAll(){
        //return this.jdbcTemplate.query("Select * from pizza", new BeanPropertyRowMapper<>(PizzaEntity.class)); /*Permite crear consultas sql y convertilas en clases java*/
        return this.pizzaRepository.findAll();
    }

    public List<PizzaEntity> getAvailable(){
        //this.pizzaRepository.countByVeganTrue();
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }
    public List<PizzaEntity> getVegan(){
        return this.pizzaRepository.findAllByVeganTrueOrderByName();
    }

    public List<PizzaEntity> getName(){
        return this.pizzaRepository.findAllByAvailableTrueOrderByName();
    }

    public PizzaEntity getByName(String name){
        return this.pizzaRepository.findAllByAvailableTrueAndNameIgnoreCase(name);
    }

    public PizzaEntity getByNameOptional(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(()->new RuntimeException("La pizza no existe"));
    }

    public List<PizzaEntity> getWith(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithout(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getCheapestPizzas(double price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
    }

    public boolean exists(int idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }

    public void delete(int idPizza){
        this.pizzaRepository.deleteById(idPizza);
    }
}
