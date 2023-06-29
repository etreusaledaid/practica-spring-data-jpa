package com.ejemplo.java.pizzeria.service;

import com.ejemplo.java.pizzeria.persistence.entity.OrderEntity;
import com.ejemplo.java.pizzeria.persistence.projection.OrderSummary;
import com.ejemplo.java.pizzeria.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String OUTSIDE = "S";

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll(){
        List<OrderEntity> orders = this.orderRepository.findAll();/*Para mandar traer de quien es la orden*/
        orders.forEach(o -> System.out.println(o.getCustomer().getName()));
        return orders;
        //return this.orderRepository.findAll();
    }
    public List<OrderEntity> getTodayOrders(){
        LocalDateTime today = LocalDateTime.now().toLocalDate().atTime(0,0);
        return this.orderRepository.findAllByDateAfter(today);
    }
    public List<OrderEntity> getOutsideOrders(){
        List<String> methods = Arrays.asList(DELIVERY,CARRYOUT);/*D:Delivery C:Carry out*/
        return this.orderRepository.findAllByMethodIn(methods);
    }

    public List<OrderEntity> getCustomerOrders(String idCustomer){
        return this.orderRepository.findCustomerOrders(idCustomer);
    }

    public OrderSummary getSummary(int orderId){
        return this.orderRepository.findSummary(orderId);
    }
}
