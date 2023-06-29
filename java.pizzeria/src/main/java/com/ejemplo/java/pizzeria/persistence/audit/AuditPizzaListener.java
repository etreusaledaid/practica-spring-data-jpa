package com.ejemplo.java.pizzeria.persistence.audit;

import com.ejemplo.java.pizzeria.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

/**Crear listener personalizado para auditoría*/
public class AuditPizzaListener {
    private PizzaEntity currentValue;

    /*Se ejecutara este metodo despues que se haga un select y se cargue un entity en el proceso*/
    @PostLoad
    public void postLoad(PizzaEntity entity){
        System.out.println("POST LOAD");
        this.currentValue = SerializationUtils.clone(entity);
    }

    /*Cuando exista una actualización en pizza se ejecutara*/
    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity entity){
        System.out.println("POST PERSIST UPDATE");
        System.out.println("OLD VALUE: "+this.currentValue.toString());
        System.out.println("NEW VALUE: "+entity.toString());
    }

    /*Se ejecutara antes de eliminarse de la base de datos*/
    @PreRemove
    public void onPreDelete(PizzaEntity entity){
        System.out.println(entity.toString());
    }
}
