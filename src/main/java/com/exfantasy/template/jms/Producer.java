package com.exfantasy.template.jms;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.exfantasy.template.cnst.QueueName;

@Component
public class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	@Autowired
	private JmsMessagingTemplate jmsMessageTemplate;
	
	@Autowired
	private Queue testingQ;

	public void sendToTestingQ(String msg) {
		jmsMessageTemplate.convertAndSend(this.testingQ, msg);
		logger.info("Send msg: <{}> to queue: <{}> succeed", msg, QueueName.TESTING_Q);
	}

}
