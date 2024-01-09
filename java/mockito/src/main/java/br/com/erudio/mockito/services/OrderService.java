package br.com.erudio.mockito.services;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderService {

    public Order createOrder(String productName, Long amout, String orderId) {
        Order order = new Order();

        order.setId(orderId == null ? UUID.randomUUID().toString() : orderId);
        order.setCreationDate(LocalDateTime.now());
        order.setAmount(amout);
        order.setProductName(productName);

        return order;
    }
}
