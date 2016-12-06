package com.exfantasy.template.services.consume;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exfantasy.template.mybatis.custom.CustomReceiptRewardMapper;
import com.exfantasy.template.mybatis.mapper.ConsumeMapper;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.ConsumeExample;
import com.exfantasy.template.mybatis.model.ConsumeExample.Criteria;
import com.exfantasy.template.mybatis.model.ReceiptReward;
import com.exfantasy.template.mybatis.model.ReceiptRewardExample;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.vo.request.ConsumeVo;
import com.exfantasy.utils.date.DateUtils;
import com.exfantasy.utils.tools.Bingo;
import com.exfantasy.utils.tools.ReceiptLotteryNoUtil;
import com.exfantasy.utils.tools.Reward;
import com.exfantasy.utils.tools.RewardType;

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
	
	@Autowired
	private CustomReceiptRewardMapper receiptRewardMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void addConsume(User user, ConsumeVo consumeVo) {
		Consume consume = convertConsumVoToModel(user, consumeVo);
		consumeMapper.insert(consume);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updConsume(User user, ConsumeVo consumeVo) {
		Consume consume = convertConsumVoToModel(user, consumeVo);
		consumeMapper.updateByPrimaryKey(consume);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void delConsume(User user, ConsumeVo consumeVo) {
		Consume consume = convertConsumVoToModel(user, consumeVo);
		consumeMapper.deleteByPrimaryKey(consume.getLotteryNo());
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

		if (consumes.size() != 0) {
			consumes.sort((obj1, obj2) -> obj1.getConsumeDate().compareTo(obj2.getConsumeDate()));
			getLatestReceiptLotteryNo();
			checkIsGot(consumes);
		}
		
		return consumes;
	}

	private Consume convertConsumVoToModel(User user, ConsumeVo consumeVo) {
		Consume consume = new Consume();
		consume.setUserId(user.getUserId());
		consume.setConsumeDate(DateUtils.asDate(consumeVo.getConsumeDate()));
		consume.setType(consumeVo.getType());
		consume.setProdName(consumeVo.getProdName());
		consume.setAmount(consumeVo.getAmount());
		consume.setLotteryNo(consumeVo.getLotteryNo());
		return consume;
	}

	private void getLatestReceiptLotteryNo() {
		// 用來 batch delete
		List<String> sectionsToDelete = new ArrayList<>();
		
		// 用來 batch Insert
		List<ReceiptReward> receiptRewardsToInsert = new ArrayList<>();
		
		List<Reward> rewards = ReceiptLotteryNoUtil.getReceiptLotteryNo();
		for (Reward reward : rewards) {
			String section = reward.getSection();
			int rewardType = reward.getRewardType().getCode();
			String number = reward.getNo();
			
			// 準備將 db 中已存在資料刪除
			if (!sectionsToDelete.contains(section)) {
				sectionsToDelete.add(section);
			}
			
			// 將網路查回來的 Reward 轉換為 ReceiptReward 加到 list 中準備 batch insert
			ReceiptReward receiptReward = new ReceiptReward();
			receiptReward.setSection(section);
			receiptReward.setRewardType(rewardType);
			receiptReward.setNumber(number);
			receiptRewardsToInsert.add(receiptReward);
		}
		
		receiptRewardMapper.batchDeleteBySection(sectionsToDelete);
		
		receiptRewardMapper.batchInsert(receiptRewardsToInsert);
	}

	private void checkIsGot(List<Consume> consumes) {
		// 暫存每一期的資料
		Map<String, List<ReceiptReward>> receiptRewardMap = new HashMap<>();
		
		for (Consume consume : consumes) {
			String section = ReceiptLotteryNoUtil.getSection(consume.getConsumeDate());
			
			List<ReceiptReward> receiptRewards = new ArrayList<>();
			
			// 若暫存區沒有值, 嘗試去 DB 撈取
			if (!receiptRewardMap.containsKey(section)) {
				// 用來查詢 DB 此期別開獎資訊
				ReceiptRewardExample example = new ReceiptRewardExample();
				example.createCriteria().andSectionEqualTo(section);
				List<ReceiptReward> dbDatas = receiptRewardMapper.selectByExample(example);
				if (dbDatas.size() != 0) {
					receiptRewardMap.put(section, dbDatas);
					receiptRewards.addAll(dbDatas);
				}
				else {
					receiptRewardMap.put(section, null);
				}
			}
			// 暫存區有, 判斷是否為 null, 在決定要不要加
			else {
				List<ReceiptReward> memDatas = receiptRewardMap.get(section);
				if (memDatas != null) {
					receiptRewards.addAll(memDatas);
				}
			}
			
			// 代表未開獎
			if (receiptRewards.size() == 0) {
				consume.setGot(-1);
			}
			// 代表已開獎
			else {
				// 轉換成 rewards 物件給 util 對獎
				List<Reward> rewards = convertReceiptRewardToReward(receiptRewards);
				
				// 進行對獎
				String lotteryNo = consume.getLotteryNo();
				Bingo bingo = ReceiptLotteryNoUtil.checkIsBingo(lotteryNo, rewards);
				
				boolean isBingo = bingo.isBingo();
				
				// 中獎
				if (isBingo) {
					long prize = bingo.getPrize();
					logger.info(">>>>> Section: {}, lotteryNo: {} is bingo, prize: {}", section, lotteryNo, prize);
					consume.setGot(1);
					consume.setPrize(prize);
				}
				// 未中獎
				else {
					consume.setGot(0);
				}
			}
			// 更新 DB 狀態
			consumeMapper.updateByPrimaryKeySelective(consume);
		}
	}
	
	/**
	 * 將 ReceiptReward 轉換為 Reward 給 ReceiptLotteryNoUtil 對獎
	 * 
	 * @param receiptRewards
	 * @return
	 */
	private List<Reward> convertReceiptRewardToReward(List<ReceiptReward> receiptRewards) {
		List<Reward> rewards = new ArrayList<>();
		for (ReceiptReward receiptReward : receiptRewards) {
			Reward reward = new Reward();
			reward.setSection(receiptReward.getSection());
			reward.setRewardType(RewardType.convertByCode(receiptReward.getRewardType()));
			reward.setNo(receiptReward.getNumber());
			rewards.add(reward);
		}
		return rewards;
	}
}