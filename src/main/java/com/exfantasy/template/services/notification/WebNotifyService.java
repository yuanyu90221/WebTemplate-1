package com.exfantasy.template.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.exfantasy.template.vo.notification.NotificationMsg;

@Service
public class WebNotifyService {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public void notify(String email, NotificationMsg msg) {
		messagingTemplate.convertAndSendToUser(email, "/queue/notify", msg);
	}
	
	public void sendSurprise(String email, NotificationMsg msg) {
		messagingTemplate.convertAndSendToUser(email, "/surprise", msg);
	}
}
