package com.exfantasy.template.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.exfantasy.template.vo.notification.NotificationMsg;

/**
 * <pre>
 * 發送 Notify 訊息用的 service
 * 
 * 參考: <a href="https://github.com/netgloo/spring-boot-samples/tree/master/spring-boot-web-socket-user-notifications">Spring Boot Web Socket User Notifications</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
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
