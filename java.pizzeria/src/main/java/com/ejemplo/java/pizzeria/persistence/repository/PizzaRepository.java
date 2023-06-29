package com.ejemplo.java.pizzeria.persistence.repository;

import com.ejemplo.java.pizzeria.persistence.entity.PizzaEntity;
import com.ejemplo.java.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

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

    /**Usar @Modifying en un @Query*/
    /*@Query(value = "UPDATE pizza" +
            "SET price = :newPrice " +
            "WHERE id_pizza = :idPizza", nativeQuery = true)
    void updatePrice(@Param("idPizza") int idPizza, @Param("newPrice") double price);*/

    /*:#{#newPizzaPrice.newPrice} es spring expression language*/
    @Query(value = "UPDATE pizza " +
            "SET price = :#{#newPizzaPrice.newPrice} " +
            "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}", nativeQuery = true)
    @Modifying
    void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);
}
