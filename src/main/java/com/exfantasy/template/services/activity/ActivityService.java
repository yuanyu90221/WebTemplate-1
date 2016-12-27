package com.exfantasy.template.services.activity;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exfantasy.template.mybatis.mapper.ActivityMapper;
import com.exfantasy.template.mybatis.mapper.JoinActivitiesMapper;
import com.exfantasy.template.mybatis.model.Activity;
import com.exfantasy.template.mybatis.model.ActivityExample;
import com.exfantasy.template.mybatis.model.ActivityExample.Criteria;
import com.exfantasy.template.mybatis.model.JoinActivitiesKey;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.vo.request.ActivityVo;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ActivityService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private JoinActivitiesMapper joinActivitiesMapper;

	/**
	 * <pre>
	 * 建立新活動
	 * </pre>
	 * 
	 * @param user
	 * @param activityVo
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void createActivity(User user, ActivityVo activityVo) {
		Activity activity = convertActivityVoToModel(user, activityVo);
		activityMapper.insert(activity);
		
		JoinActivitiesKey joinActivity = new JoinActivitiesKey();
		joinActivity.setUserId(user.getUserId());
		joinActivity.setActivityId(activity.getActivityId());
		joinActivitiesMapper.insert(joinActivity);
	}

	/**
	 * 將前端傳過來的消費資料物件轉換成 mybatis model
	 * 
	 * @param user
	 * @param activityVo
	 * @return
	 */
	private Activity convertActivityVoToModel(User user, ActivityVo activityVo) {
		Activity activity = new Activity();
		activity.setCreateUserId(user.getUserId());
		activity.setCreateDate(new Date());
		activity.setTitle(activityVo.getTitle());
		activity.setDescription(activityVo.getDescription());
		activity.setStartDatetime(activityVo.getStartDatetime());
		activity.setLatitude(activityVo.getLatitude());
		activity.setLongitude(activityVo.getLongitude());
		activity.setAttendeeNum(activityVo.getAttendeeNum());
		return activity;
	}

	/**
	 * <pre>
	 * 查詢使用者所建立的活動
	 * </pre>
	 * 
	 * @param user
	 * @return
	 */
	public List<Activity> getCreatedActivities(User user) {
		ActivityExample example = new ActivityExample();
		Criteria criteria = example.createCriteria();
		criteria.andCreateUserIdEqualTo(user.getUserId());
		List<Activity> activities = activityMapper.selectByExample(example);
		return activities;
	}
}
