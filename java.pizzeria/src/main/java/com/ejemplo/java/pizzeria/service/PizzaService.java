package com.ejemplo.java.pizzeria.service;

import com.ejemplo.java.pizzeria.persistence.entity.PizzaEntity;
import com.ejemplo.java.pizzeria.persistence.repository.PizzaPagSortRepository;
import com.ejemplo.java.pizzeria.persistence.repository.PizzaRepository;
import com.ejemplo.java.pizzeria.service.dto.UpdatePizzaPriceDto;
import com.ejemplo.java.pizzeria.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service/**Es un Bean de spring*/
public class PizzaService {
    //private final JdbcTemplate jdbcTemplate;
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository; /*Inyectando interface*/

    @Autowired/**Se encarga de la inyección de dependencias*/
    /*public PizzaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    /*Este metodo retorna lo que llama la clase PizzaEntity*/
    //public List<PizzaEntity> getAll(){
        /*return this.jdbcTemplate.query("Select * from pizza", new BeanPropertyRowMapper<>(PizzaEntity.class)); /*Permite crear consultas sql y convertilas en clases java*/
        //return this.pizzaRepository.findAll();
    //}

    public Page<PizzaEntity> getAll(int page, int elements){
        PageRequest pageRequest = PageRequest.of(page, elements);
        return this.pizzaPagSortRepository.findAll(pageRequest);
    }

    /*public List<PizzaEntity> getAvailable() {
        //this.pizzaRepository.countByVeganTrue();
        //return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }*/
    public Page<PizzaEntity> getAvailable(int page, int elements, String sortBy, String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
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

    @Transactional(noRollbackFor = EmailApiException.class, propagation = Propagation.REQUIRED) /**ACID: @Transactional para asegurar la atomicidad*/
    public void updatePrice(UpdatePizzaPriceDto dto){ this.pizzaRepository.updatePrice(dto); this.SendEmail(); }

    public void SendEmail(){
        throw new EmailApiException();
    }
}
