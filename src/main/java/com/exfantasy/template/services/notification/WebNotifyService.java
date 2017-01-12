package com.exfantasy.template.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.exfantasy.template.vo.notification.NotificationMsg;

@Service
public class WebNotifyService {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public void sendAlertMsg(String email, NotificationMsg msg) {
		messagingTemplate.convertAndSendToUser(email, "/alert", msg);
	}
	
	public void sendSurpriseMsg(String email, NotificationMsg msg) {
		messagingTemplate.convertAndSendToUser(email, "/surprise", msg);
	}
}
