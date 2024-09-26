package com.example.notificationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SMSService {

	@Value("${auth_code}")
	private String authCode;

	@Value("${account_sid}")
	private String accoundId;

	public void sendNotification(String text) {
		log.info(accoundId);
		log.info(authCode);
		Twilio.init(accoundId, authCode);
		Message message = Message.creator(
				new com.twilio.type.PhoneNumber("+918600766679"),
				new com.twilio.type.PhoneNumber("+12074896719"), 
				text)
				.create();

		log.info(message.getSid());
	}

}
