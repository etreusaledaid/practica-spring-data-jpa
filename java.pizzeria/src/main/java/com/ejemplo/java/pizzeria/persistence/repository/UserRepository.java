package com.ejemplo.java.pizzeria.persistence.repository;

import com.ejemplo.java.pizzeria.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
