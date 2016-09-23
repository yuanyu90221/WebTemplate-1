package com.exfantasy.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/mail")
public class MailController {

	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	@ResponseBody
	public String sendMail() {
		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo("tommy.yeh1112@gmail.com");
		mail.setSubject("This is a test from SpringBoot");
		mail.setText("Hello~~");
		mailSender.send(mail);

		return "Send mail succeed";
	}
}