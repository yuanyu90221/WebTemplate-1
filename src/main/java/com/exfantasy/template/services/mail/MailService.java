package com.exfantasy.template.services.mail;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.exfantasy.template.cnst.MailTemplateType;
import com.exfantasy.template.mybatis.mapper.MailTemplateMapper;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.MailTemplateExample;
import com.exfantasy.template.mybatis.model.MailTemplateExample.Criteria;
import com.exfantasy.template.mybatis.model.MailTemplateWithBLOBs;
import com.exfantasy.template.mybatis.model.User;

/**
 * 處理發送 email 邏輯
 * 
 * @author tommy.feng
 *
 */
@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailTemplateMapper mailTemplateMapper;
	
	/**
	 * 發送 email
	 * 
	 * @param mailTo 收件者信箱
	 * @param subject 主旨
	 * @param mailContent 內容
	 * @throws MessagingException
	 */
	public void sendMail(String mailTo, String subject, String mailContent) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		mimeMessage.setSubject(subject);
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		helper.setTo(mailTo);
		helper.setText(mailContent, true);
		
		mailSender.send(mimeMessage);
	}

	/**
	 * 將消費資料中獎資訊發送給使用者
	 * 
	 * @param user
	 * @param gotItConsumes
	 */
	public void sendGotItMail(User user, List<Object> gotItConsumes) {
		// 暫存要填入 html 的參數
		List<Object> args = new ArrayList<>();
		
		// {0} 為使用者 email
		args.add(user.getEmail());
		
		// 取得 Mail Template
		MailTemplateExample example = new MailTemplateExample();
		Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(MailTemplateType.consumeGotIt.toString());
		List<MailTemplateWithBLOBs> mailTemplates = mailTemplateMapper.selectByExampleWithBLOBs(example);
		
		MailTemplateWithBLOBs mailTemplate = mailTemplates.get(0);
		
		// 根據中獎筆數組出中獎資訊
		StringBuilder mailContentBuffer = new StringBuilder();
		mailContentBuffer.append(mailTemplate.getHeader());
		for (int i = 0; i < gotItConsumes.size(); i++) {
			// 組出中獎內容 Template
			mailContentBuffer.append(mailTemplate.getBodyHeader());
			mailContentBuffer.append("{").append(i + 1).append("}");
			mailContentBuffer.append(mailTemplate.getBodyTail());
			
			// 將中獎資訊塞入參數中
			Object[] gotItSectionAndConsume = (Object[]) gotItConsumes.get(i);
			String section = (String) gotItSectionAndConsume[0];
			Consume gotItConsume = (Consume) gotItSectionAndConsume[1];
			args.add("月份：" + section + "<br>號碼：" + gotItConsume.getLotteryNo() + "，獎金：" + gotItConsume.getPrize());
		}
		mailContentBuffer.append(mailTemplate.getTail());
		
		String mailContent = MessageFormat.format(mailContentBuffer.toString(), args.toArray(new Object[0]));
		
		// 發信
		try {
			sendMail(user.getEmail(), mailTemplate.getSubject(), mailContent);
		} catch (MessagingException e) {
			logger.warn("Send got it mail failed", e);
		}
	}

	public void sendForgotPasswordMail(String email, String randomPassword) {
		// 暫存要填入 html 的參數
		List<Object> args = new ArrayList<>();
		
		// {0} 為使用者 email
		args.add(email);
		
		// {1} 為新產生密碼
		args.add(randomPassword);
		
		// 取得 Mail Template
		MailTemplateExample example = new MailTemplateExample();
		Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(MailTemplateType.forgotPassword.toString());
		List<MailTemplateWithBLOBs> mailTemplates = mailTemplateMapper.selectByExampleWithBLOBs(example);
		
		MailTemplateWithBLOBs mailTemplate = mailTemplates.get(0);
		
		// 將新產生的密碼填入
		StringBuilder mailContentBuffer = new StringBuilder();
		mailContentBuffer.append(mailTemplate.getHeader());
		mailContentBuffer.append(mailTemplate.getBodyHeader());
		mailContentBuffer.append("{1}");
		mailContentBuffer.append(mailTemplate.getBodyTail());
		mailContentBuffer.append(mailTemplate.getTail());
		
		String mailContent = MessageFormat.format(mailContentBuffer.toString(), args.toArray(new Object[0]));
		
		// 發信
		try {
			sendMail(email, mailTemplate.getSubject(), mailContent);
		} catch (MessagingException e) {
			logger.warn("Send forgot password mail failed", e);
		}
	}
}
