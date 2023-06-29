package com.ejemplo.java.pizzeria.persistence.entity;

import com.ejemplo.java.pizzeria.persistence.audit.AuditPizzaListener;
import com.ejemplo.java.pizzeria.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity/**Le indica que se comportara esta clase como una entidad que sera traducida a una tabla*/
@Table(name = "pizza")
@EntityListeners({AuditingEntityListener.class, AuditPizzaListener.class}) /**Auditoría con Spring Data*/
@Getter
@Setter
@NoArgsConstructor
//public class PizzaEntity {
public class PizzaEntity extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pizza", nullable = false)/*El name es por si el nombre de la variable es diferente a la base de datos*/
    private Integer idPizza;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")/*Con columnDefinition define los decimales*/
    private Double price;

    @Column(columnDefinition = "TINYINT")
    private Boolean vegetarian;

    @Column(columnDefinition = "TINYINT")
    private Boolean vegan;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Boolean available;

    /**Auditoría con Spring Data*/
    /*@Column(name = "created_date")
    @CreatedDate
    @JsonIgnore //Ignorar este dato al traer el json con la información
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;*/
}
