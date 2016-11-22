package com.exfantasy.template.services.consume;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exfantasy.template.mybatis.mapper.ConsumeMapper;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.ConsumeExample;
import com.exfantasy.template.mybatis.model.ConsumeExample.Criteria;
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

	public List<Consume> getConsume(Date startDate, Date endDate, Integer type, String prodName, String lotteryNo) {
		ConsumeExample example = new ConsumeExample();
		Criteria criteria = example.createCriteria();
		if (startDate != null && endDate == null) {
			criteria.andConsumeDateGreaterThanOrEqualTo(startDate);
		}
		if (startDate == null && endDate != null) {
			criteria.andConsumeDateLessThanOrEqualTo(endDate);
		}
		if (startDate != null && endDate != null) {
			criteria.andConsumeDateBetween(startDate, endDate);
		}
		if (type != null) {
			criteria.andTypeEqualTo(type);
		}
		if (prodName != null) {
			criteria.andProdNameLike("%" + prodName + "%");
		}
		if (lotteryNo != null) {
			criteria.andLotteryNoEqualTo(lotteryNo);
		}
		List<Consume> consumes = consumeMapper.selectByExample(example);
		consumes.sort((obj1, obj2) -> obj1.getConsumeDate().compareTo(obj2.getConsumeDate()));
		return consumes;
	}
}