package com.exfantasy.template.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import com.exfantasy.template.cnst.QueueName;

@Configuration
@EnableJms
public class JmsConfig {

	@Bean
	public Queue testingQ() {
		return new ActiveMQQueue(QueueName.TESTING_Q);
	}
}
