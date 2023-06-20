package com.ejemplo.java.pizzeria.persistence.repository;

import com.ejemplo.java.pizzeria.persistence.entity.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
}
