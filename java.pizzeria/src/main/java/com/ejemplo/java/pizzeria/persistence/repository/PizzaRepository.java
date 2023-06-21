package com.ejemplo.java.pizzeria.persistence.repository;

import com.ejemplo.java.pizzeria.persistence.entity.PizzaEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();/**Query Methods: And, Or, True, IgnoreCase, OrderBy*/
    List<PizzaEntity> findAllByAvailableTrueOrderByName();
    PizzaEntity findAllByAvailableTrueAndNameIgnoreCase(String name);/**Query Methods: First, Top, Optional, LessThan, GreaterThan*/
    /**Query Methods: Contains, Not*/
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);
    //int countByVeganTrue();
    List<PizzaEntity> findAllByVeganTrueOrderByName();
    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(Double price);
}
