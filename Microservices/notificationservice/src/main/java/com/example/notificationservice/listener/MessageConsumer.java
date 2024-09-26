package com.example.notificationservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.notificationservice.service.SMSService;

import lombok.extern.slf4j.Slf4j;

import static com.example.notificationservice.util.Constant.ORDER_STATUS_QUEUE_NAME;

@Service
@Slf4j
public class MessageConsumer {
	
	@Autowired
	private SMSService smsService;

    @RabbitListener(queues = ORDER_STATUS_QUEUE_NAME)
    public void receiveMessage(String message) {
        log.info("Received message: {}", message);
        smsService.sendNotification(message);
    }
}

