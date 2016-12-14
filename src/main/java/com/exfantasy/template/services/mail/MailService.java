package com.exfantasy.template.services.mail;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.exfantasy.template.cnst.MailTemplateType;
import com.exfantasy.template.mybatis.mapper.MailTemplateMapper;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.MailTemplateExample;
import com.exfantasy.template.mybatis.model.MailTemplateExample.Criteria;
import com.exfantasy.template.mybatis.model.MailTemplateWithBLOBs;
import com.exfantasy.template.mybatis.model.User;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailTemplateMapper mailTemplateMapper;
	
	public void sendMail(String mailTo, String subject, String text) {
		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(mailTo);
		mail.setSubject(subject);
		mail.setText(text);

		mailSender.send(mail);
	}

	public void sendGotItMail(User user, List<Consume> gotItConsumes) {
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
			Consume gotItConsume = gotItConsumes.get(i);
			args.add("號碼: " + gotItConsume.getLotteryNo() + ", 獎金: " + gotItConsume.getPrize());
		}
		mailContentBuffer.append(mailTemplate.getTail());
		
		String mailContent = MessageFormat.format(mailContentBuffer.toString(), args.toArray(new Object[0]));
		
		// 發信
		sendMail(user.getEmail(), "恭喜您發票中獎", mailContent);
	}
}
