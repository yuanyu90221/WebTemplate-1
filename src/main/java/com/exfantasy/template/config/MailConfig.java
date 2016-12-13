package com.exfantasy.template.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * <pre>
 * Mail 設定, 此處設定可參照 application.yaml
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Configuration
public class MailConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);

	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		logger.debug(">>>>> Gmail username: <{}>, password: <{}>", username, password);

		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");

		return mailSender;
	}
}