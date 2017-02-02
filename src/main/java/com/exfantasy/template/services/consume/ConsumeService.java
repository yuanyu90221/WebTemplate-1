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

import com.exfantasy.template.mybatis.custom.CustomConsumeMapper;
import com.exfantasy.template.mybatis.custom.CustomReceiptRewardMapper;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.ConsumeExample;
import com.exfantasy.template.mybatis.model.ConsumeExample.Criteria;
import com.exfantasy.template.mybatis.model.ReceiptReward;
import com.exfantasy.template.mybatis.model.ReceiptRewardExample;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.mail.MailService;
import com.exfantasy.template.vo.request.ConsumeVo;
import com.exfantasy.template.vo.response.RespReward;
import com.exfantasy.utils.date.DateUtils;
import com.exfantasy.utils.tools.receipt_lottery.Bingo;
import com.exfantasy.utils.tools.receipt_lottery.ReceiptLotteryNoUtil;
import com.exfantasy.utils.tools.receipt_lottery.Reward;
import com.exfantasy.utils.tools.receipt_lottery.RewardType;
import com.exfantasy.utils.tools.typhoon_vacation.TyphoonVacationInfo;
import com.exfantasy.utils.tools.typhoon_vacation.TyphoonVacationUtil;

/**
 * <pre>
 * 處理消費資料相關邏輯
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
	private CustomConsumeMapper consumeMapper;
	
	@Autowired
	private CustomReceiptRewardMapper receiptRewardMapper;
	
	@Autowired
	private MailService mailService;
	
	/**
	 * <pre>
	 * 新增消費資料
	 * </pre>
	 * 
	 * @param user
	 * @param consumeVo
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void addConsume(User user, ConsumeVo consumeVo) {
		Consume consume = convertConsumVoToModel(user, consumeVo);
		consumeMapper.insert(consume);
	}

	/**
	 * <pre>
	 * 更新消費資料
	 * </pre>
	 * 
	 * @param user
	 * @param consumeVo
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updConsume(User user, ConsumeVo consumeVo) {
		Consume consume = convertConsumVoToModel(user, consumeVo);
		consumeMapper.updateByPrimaryKey(consume);
	}

	/**
	 * <pre>
	 * 刪除消費資料
	 * </pre>
	 * 
	 * @param user
	 * @param consumeVos
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void delConsumes(User user, List<ConsumeVo> consumeVos) {
		List<String> lotteryNos = new ArrayList<>();
		consumeVos.forEach(vo -> lotteryNos.add(vo.getLotteryNo()));
		consumeMapper.batchDeleteByLotteryNo(lotteryNos);
	}

	/**
	 * <pre>
	 * 根據條件查詢消費資料
	 * </pre>
	 * 
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param prodName
	 * @param lotteryNo
	 * @return
	 */
	public List<Consume> getConsume(User user, Date startDate, Date endDate, Integer type, String prodName, String lotteryNo) {
		ConsumeExample example = new ConsumeExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andUserIdEqualTo(user.getUserId());
		
		if (startDate != null && endDate == null) {
			criteria.andConsumeDateGreaterThanOrEqualTo(startDate);
		}
		if (startDate == null && endDate != null) {
			criteria.andConsumeDateLessThanOrEqualTo(endDate);
		}
		if (startDate != null && endDate != null) {
			criteria.andConsumeDateBetween(startDate, endDate);
		}
		if (type != null && type != 0) {
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
			checkIsGot(user, consumes);
		}
		
		return consumes;
	}

	/**
	 * 將前端傳過來的消費資料物件轉換成 mybatis model
	 * 
	 * @param user
	 * @param consumeVo
	 * @return
	 */
	private Consume convertConsumVoToModel(User user, ConsumeVo consumeVo) {
		Consume consume = new Consume();
		consume.setUserId(user.getUserId());
		consume.setConsumeDate(DateUtils.asDate(consumeVo.getConsumeDate()));
		consume.setType(consumeVo.getType());
		consume.setProdName(consumeVo.getProdName());
		consume.setAmount(consumeVo.getAmount());
		consume.setLotteryNo(consumeVo.getLotteryNo());
		consume.setGot(-1);
		consume.setAlreadySent(false);
		return consume;
	}

	/**
	 * <pre>
	 * 取得最新發票開獎資訊
	 * </pre>
	 */
	private List<Reward> getLatestReceiptLotteryNo() {
		List<Reward> rewards = ReceiptLotteryNoUtil.getReceiptLotteryNo();

		// 啟一個 Thread 更新 DB 資料
		new Thread(() -> {
			// 用來 batch delete
			List<String> sectionsToDelete = new ArrayList<>();
			
			// 用來 batch Insert
			List<ReceiptReward> receiptRewardsToInsert = new ArrayList<>();
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
		}).start();

		return rewards;
	}

	/**
	 * <pre>
	 * 判斷串入的消費資料是否中獎, 並且更新 DB 狀態, 若有中獎則發信給使用者
	 * </pre>
	 * 
	 * @param user
	 * @param consumes
	 */
	private void checkIsGot(User user, List<Consume> consumes) {
		// 暫存每一期的資料
		Map<String, List<ReceiptReward>> receiptRewardMap = new HashMap<>();
		
		// 暫存期號及中獎資料用來發送 mail
		List<Object> gotItSectionAndConsumes = new ArrayList<>();
		
		for (Consume consume : consumes) {
			String section = ReceiptLotteryNoUtil.getSection(consume.getConsumeDate());
			
			// 若已經對過獎的不繼續對獎流程
			if (consume.getGot() == 0 || consume.getGot() == 1) {
				// 若中獎卻沒發過信的就寄信
				if (consume.getGot() == 1 && consume.isAlreadySent() == false) {
					// 加到已中獎紀錄, 準備發送 mail
					consume.setAlreadySent(true);
					Object[] sectionAndConsume = new Object[] {section, consume};
					gotItSectionAndConsumes.add(sectionAndConsume);
				}
				continue;
			}
			
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
					
					// 加到已中獎紀錄, 準備發送 mail
					consume.setAlreadySent(true);
					Object[] sectionAndConsume = new Object[] {section, consume};
					gotItSectionAndConsumes.add(sectionAndConsume);
				}
				// 未中獎
				else {
					consume.setGot(0);
				}
			}
		}
		
		// 啟動 Thread 來做裡面的事
		new Thread(() -> {
			// 批次更新 DB 中獎狀態
			consumeMapper.batchUpdateGot(consumes);
			
			if (gotItSectionAndConsumes.size() != 0) {
				// 若發現有中獎的資料, 發送中獎通知
				mailService.sendGotItMail(user, gotItSectionAndConsumes);
				
				
				// 批次更新 DB 中寄信狀態
				List<Consume> gotItConsumes = new ArrayList<>();
				for (int i = 0; i < gotItSectionAndConsumes.size(); i++) {
					Object[] gotItSectionAndConsume = (Object[]) gotItSectionAndConsumes.get(i);
					Consume gotItConsume = (Consume) gotItSectionAndConsume[1];
					gotItConsumes.add(gotItConsume);
				}
				consumeMapper.batchUpdateAlreadtSent(gotItConsumes);
			}
		}).start();;
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

	/**
	 * 取得最新發票開獎號碼
	 * 
	 * @return List<RespReward> 回應給前端最新開獎資料
	 */
	public List<RespReward> getLatestRewardNumbers() {
		List<Reward> rewards = getLatestReceiptLotteryNo();
		List<RespReward> respRewards = convertRewardToRespReward(rewards);
		return respRewards;
	}
	
	/**
	 * 轉換 ReceiptLotteryNoUtil 回傳的 Reward 為前端可接收的資料
	 * 
	 * @param rewards ReceiptLotteryNoUtil 所使用開獎物件
	 * @return List<RespReward> 回應給前端最新開獎資料
	 */
	private List<RespReward> convertRewardToRespReward(List<Reward> rewards) {
		List<RespReward> respRewards = new ArrayList<>();
		for (Reward reward : rewards) {
			RespReward respReward = new RespReward();
			respReward.setSection(reward.getSection());
			respReward.setRewardType(reward.getRewardType().getCode());
			respReward.setNo(reward.getNo());
			respRewards.add(respReward);
		}
		return respRewards;
	}

	public List<TyphoonVacationInfo> getTyphoonVacation() {
		List<TyphoonVacationInfo> typhoonVactionInfos = TyphoonVacationUtil.getTyphoonVactionInfo();
		return typhoonVactionInfos;
	}
}