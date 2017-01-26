package com.exfantasy.template.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.exfantasy.template.cnst.QueueName;

@Component
public class Consumer {
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(destination = QueueName.TESTING_Q)
	public void receiveQueue(String text) {
		logger.info("Received message: <{}> from queue: <{}>", text, QueueName.TESTING_Q);
	}
}
