package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String INVENTORY_SERVICE_URL = "http://localhost:8081/api/inventory/items";

    public Order placeOrder(Order order) {
        // Check inventory for each item
        for (int i = 0; i < order.getItemIds().size(); i++) {
            Long itemId = order.getItemIds().get(i);
            int quantity = order.getItemQuantities().get(i);

            Boolean isAvailable = restTemplate.getForObject(
                INVENTORY_SERVICE_URL + "/" + itemId + "/available?quantity=" + quantity, Boolean.class
            );

            if (isAvailable == null || !isAvailable) {
                throw new RuntimeException("Item ID " + itemId + " is not available in the required quantity.");
            }

            // Reserve the inventory
            restTemplate.put(
                INVENTORY_SERVICE_URL + "/" + itemId + "/reserve?quantity=" + quantity, null
            );
        }

        // Save order with status 'PENDING'
        order.setOrderStatus("PENDING");
        return orderRepository.save(order);
    }

    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found."));

        // Deduct inventory after order completion
        for (int i = 0; i < order.getItemIds().size(); i++) {
            Long itemId = order.getItemIds().get(i);
            int quantity = order.getItemQuantities().get(i);

            restTemplate.put(
                INVENTORY_SERVICE_URL + "/" + itemId + "/deduct?quantity=" + quantity, null
            );
        }

        // Update order status to 'COMPLETED'
        order.setOrderStatus("COMPLETED");
        orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found."));

        // Release the reserved inventory
        for (int i = 0; i < order.getItemIds().size(); i++) {
            Long itemId = order.getItemIds().get(i);
            int quantity = order.getItemQuantities().get(i);

            restTemplate.put(
                INVENTORY_SERVICE_URL + "/" + itemId + "/release?quantity=" + quantity, null
            );
        }

        // Update order status to 'CANCELLED'
        order.setOrderStatus("CANCELLED");
        orderRepository.save(order);
    }
}
