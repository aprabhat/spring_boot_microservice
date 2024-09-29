package com.example.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private MessageProducer messageProducer;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	DiscoveryClient discoveryClient;

	@Value("${INVENTORY_SERVICE_URL}")
	private String inventoryServiceUrl;

	public Order placeOrder(Order order) {
		log.info("inventoryServiceUrl from properties file {}", inventoryServiceUrl);
		log.info("inventoryServiceUrl from eureka client {}", discoveryClient.getInstances("inventoryservice").get(0).getUri());
		// Check inventory for each item
		for (int i = 0; i < order.getItemIds().size(); i++) {
			Long itemId = order.getItemIds().get(i);
			int quantity = order.getItemQuantities().get(i);

			Boolean isAvailable = restTemplate.getForObject(
					discoveryClient.getInstances("inventoryservice").get(0).getUri() + "/api/inventory/items/" + itemId + "/available?quantity=" + quantity, Boolean.class);

			if (isAvailable == null || !isAvailable) {
				log.error("item not available");
				throw new RuntimeException("Item ID " + itemId + " is not available in the required quantity.");
			}

			// Reserve the inventory
			restTemplate.put(discoveryClient.getInstances("inventoryservice").get(0).getUri() + "/api/inventory/items/" + itemId + "/reserve?quantity=" + quantity, null);
		}

		// Save order with status 'PENDING'
		order.setOrderStatus("PENDING");
		messageProducer.sendMessage("order placed successfully for "+ order);
		Order orderEntity = orderRepository.save(order);
		log.info("data saved successfully {}", orderEntity);
		return orderEntity;
	}

	public void completeOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found."));

		// Deduct inventory after order completion
		for (int i = 0; i < order.getItemIds().size(); i++) {
			Long itemId = order.getItemIds().get(i);
			int quantity = order.getItemQuantities().get(i);

			restTemplate.put(inventoryServiceUrl + "/" + itemId + "/deduct?quantity=" + quantity, null);
		}

		// Update order status to 'COMPLETED'
		order.setOrderStatus("COMPLETED");
		orderRepository.save(order);
	}

	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found."));

		// Release the reserved inventory
		for (int i = 0; i < order.getItemIds().size(); i++) {
			Long itemId = order.getItemIds().get(i);
			int quantity = order.getItemQuantities().get(i);

			restTemplate.put(inventoryServiceUrl + "/" + itemId + "/release?quantity=" + quantity, null);
		}

		// Update order status to 'CANCELLED'
		order.setOrderStatus("CANCELLED");
		orderRepository.save(order);
	}
}
