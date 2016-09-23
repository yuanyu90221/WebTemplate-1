package com.exfantasy.template.services.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMail(String mailTo, String subject, String text) {
		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(mailTo);
		mail.setSubject(subject);
		mail.setText(text);

		mailSender.send(mail);
	}
}
