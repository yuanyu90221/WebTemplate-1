package com.exfantasy.template.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import com.exfantasy.template.cnst.QueueName;

/**
 * <pre>
 * ActiveMQ 設定
 * 
 * PS: 要新增 Queue 可參考 method: testingQ
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Configuration
@EnableJms
public class JmsConfig {

	@Bean
	public Queue testingQ() {
		return new ActiveMQQueue(QueueName.TESTING_Q);
	}
}
