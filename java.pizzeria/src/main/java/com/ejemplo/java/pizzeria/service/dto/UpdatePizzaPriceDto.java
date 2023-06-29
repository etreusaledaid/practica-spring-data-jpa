package com.ejemplo.java.pizzeria.service.dto;

import lombok.Data;

/*DTO: data transfer object*/
@Data
public class UpdatePizzaPriceDto {
    private int pizzaId;
    private int newPrice;
}
