package com.exfantasy.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.services.mail.MailService;

@Controller
@RequestMapping(value = "/mail")
public class MailController {

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public @ResponseBody String sendMail() {
		String mailTo = "tommy.yeh1112@gmail.com";
		String subject = "This is a test from SpringBoot";
		String text = "Hello~~";

		mailService.sendMail(mailTo, subject, text);

		return "Send mail succeed";
	}
}