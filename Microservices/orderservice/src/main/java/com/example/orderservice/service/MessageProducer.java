package com.example.orderservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import static com.example.orderservice.util.Constant.ORDER_STATUS_QUEUE_NAME;

@Service
@Slf4j
public class MessageProducer {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public MessageProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String message) {
		log.info("Sending message: {}", message);
		rabbitTemplate.convertAndSend(ORDER_STATUS_QUEUE_NAME, message);
	}
}
