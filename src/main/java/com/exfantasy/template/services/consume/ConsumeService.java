package com.exfantasy.template.services.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exfantasy.template.mybatis.mapper.ConsumeMapper;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.util.DateUtils;
import com.exfantasy.template.vo.request.ConsumeVo;

/**
 * <pre>
 * 處理記帳相關邏輯
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ConsumeService {
	private static final Logger logger = LoggerFactory.getLogger(ConsumeService.class);
	
	@Autowired
	private ConsumeMapper consumeMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void addConsume(User user, ConsumeVo consumeVo) {
		Consume consume = new Consume();

		consume.setUserId(user.getUserId());
		consume.setConsumeDate(DateUtils.asDate(consumeVo.getConsumeDate()));
		consume.setType(consumeVo.getType());
		consume.setProdName(consumeVo.getProdName());
		consume.setAmount(consumeVo.getAmount());
		consume.setLotteryNo(consumeVo.getLotteryNo());
		
		consumeMapper.insert(consume);
	}
}