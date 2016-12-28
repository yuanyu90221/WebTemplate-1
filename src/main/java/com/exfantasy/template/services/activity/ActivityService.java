package com.exfantasy.template.services.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.custom.CustomActivityMessagesMapper;
import com.exfantasy.template.mybatis.mapper.ActivityMapper;
import com.exfantasy.template.mybatis.mapper.JoinActivitiesMapper;
import com.exfantasy.template.mybatis.mapper.UserMapper;
import com.exfantasy.template.mybatis.model.Activity;
import com.exfantasy.template.mybatis.model.ActivityExample;
import com.exfantasy.template.mybatis.model.ActivityMessages;
import com.exfantasy.template.mybatis.model.JoinActivitiesExample;
import com.exfantasy.template.mybatis.model.JoinActivitiesKey;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.mybatis.model.UserExample;
import com.exfantasy.template.vo.request.ActivityVo;
import com.exfantasy.template.vo.response.ActivityMessagesResp;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ActivityService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired
    private UserMapper userMapper;
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private JoinActivitiesMapper joinActivitiesMapper;
	
	@Autowired
	private CustomActivityMessagesMapper customActivityMessagesMapper;

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
	 * <pre>
	 * 將前端傳過來的消費資料物件轉換成 mybatis model
	 * </pre>
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
	 * 參加某一個活動
	 * </pre>
	 * 
	 * @param userId
	 * @param activityId
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void joinActivity(Integer userId, Integer activityId) {
		// 若此查詢為空, 但表此活動不存在
		Activity activity = getActivityByActivityId(activityId);
		if (activity == null) {
			throw new OperationException(ResultCode.ACTIVITY_NOT_EXISTED);
		}
		
		// 若此查詢不為空, 但表此活動此使用者已經參加
		JoinActivitiesKey joinedActivity = getJoinedActivitiesByUserIdAndActivityId(userId, activityId);
		if (joinedActivity != null) {
			throw new OperationException(ResultCode.ACTIVITY_ALREADY_JOINED);
		}
		
		JoinActivitiesKey record = new JoinActivitiesKey();
		record.setUserId(userId);
		record.setActivityId(activityId);
		joinActivitiesMapper.insert(record);
	}

	/**
	 * <pre>
	 * 根據活動 ID 取得對應活動
	 * </pre>
	 * 
	 * @param activityId
	 * @return
	 */
	private Activity getActivityByActivityId(Integer activityId) {
		ActivityExample activityExample = new ActivityExample();
		activityExample.createCriteria()
			.andActivityIdEqualTo(activityId);
		List<Activity> activity = activityMapper.selectByExample(activityExample);
		return activity.size() != 0 ? activity.get(0) : null;
	}

	/**
	 * <pre>
	 * 根據使用者 ID 及活動 ID 取得參加資訊
	 * </pre>
	 * 
	 * @param userId
	 * @param activityId
	 * @return
	 */
	private JoinActivitiesKey getJoinedActivitiesByUserIdAndActivityId(Integer userId, Integer activityId) {
		JoinActivitiesExample joinActivitiesExample = new JoinActivitiesExample();
		joinActivitiesExample.createCriteria()
			.andUserIdEqualTo(userId)
			.andActivityIdEqualTo(activityId);
		List<JoinActivitiesKey> joinedActivities = joinActivitiesMapper.selectByExample(joinActivitiesExample);
		return joinedActivities.size() != 0 ? joinedActivities.get(0) : null;
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
		ActivityExample activityExample = new ActivityExample();
		activityExample.createCriteria().andCreateUserIdEqualTo(user.getUserId());
		List<Activity> activities = activityMapper.selectByExample(activityExample);
		return activities;
	}

	/**
	 * <pre>
	 * 查詢使用者參與的所有的活動
	 * </pre>
	 * 
	 * @param user
	 * @return
	 */
	public List<Activity> getJoinedActivities(User user) {
		List<JoinActivitiesKey> joinedActivities = getJoinedActivitiesByUserId(user.getUserId());
		
		List<Integer> joinedActivitiesId = new ArrayList<>();
		for (JoinActivitiesKey joinedActivitie : joinedActivities) {
			Integer joinedActivityId = joinedActivitie.getActivityId();
			joinedActivitiesId.add(joinedActivityId);
		}
		
		// 有可能此 User 未建立且未參與任何活動
		List<Activity> activities = new ArrayList<>();
		if (joinedActivitiesId.size() != 0) {
			ActivityExample activityExample = new ActivityExample();
			activityExample.createCriteria().andActivityIdIn(joinedActivitiesId);
			activities = activityMapper.selectByExample(activityExample);
		}
		
		return activities;
	}
	
	/**
	 * <pre>
	 * 查詢參與活動的所有使用者
	 * </pre>
	 * 
	 * @param activityId
	 * @return
	 */
	public List<User> getJoinedUsers(Integer activityId) {
		List<JoinActivitiesKey> joinedUsers = getJoinedUsersByActivityId(activityId);
		
		List<Integer> joinedUsersId = new ArrayList<>();
		for (JoinActivitiesKey joinedUser : joinedUsers) {
			Integer joinedUserId = joinedUser.getUserId();
			joinedUsersId.add(joinedUserId);
		}

		// 有可能傳入一個未存在的 ActivityId, 所以還是判斷一下
		List<User> usersInfo = new ArrayList<>();
		if (joinedUsersId.size() != 0) {
			UserExample userExample = new UserExample();
			userExample.createCriteria().andUserIdIn(joinedUsersId);
			usersInfo = userMapper.selectByExample(userExample);
		}
		
		return usersInfo;
	}

	/**
	 * <pre>
	 * 根據 userId 查詢已參與的活動
	 * </pre>
	 * 
	 * @param userId
	 * @return
	 */
	private List<JoinActivitiesKey> getJoinedActivitiesByUserId(Integer userId) {
		JoinActivitiesExample joinActivitiesExample = new JoinActivitiesExample();
		joinActivitiesExample.createCriteria().andUserIdEqualTo(userId);
		List<JoinActivitiesKey> joinedActivities = joinActivitiesMapper.selectByExample(joinActivitiesExample);
		return joinedActivities;
	}
	
	/**
	 * <pre>
	 * 根據 activityId 查詢已參與的使用者
	 * </pre>
	 * 
	 * @param activityId
	 * @return
	 */
	private List<JoinActivitiesKey> getJoinedUsersByActivityId(Integer activityId) {
		JoinActivitiesExample joinActivitiesExample = new JoinActivitiesExample();
		joinActivitiesExample.createCriteria().andActivityIdEqualTo(activityId);
		List<JoinActivitiesKey> joinedUsers = joinActivitiesMapper.selectByExample(joinActivitiesExample);
		return joinedUsers;
	}

	/**
	 * <pre>
	 * 對某一個活動留言
	 * </pre>
	 * 
	 * @param userId
	 * @param activityId
	 * @param message
	 */
	public void leaveMessage(Integer userId, Integer activityId, String message) {
		// 若此查詢為空, 但表此活動不存在
		Activity activity = getActivityByActivityId(activityId);
		if (activity == null) {
			throw new OperationException(ResultCode.ACTIVITY_NOT_EXISTED);
		}
		
		ActivityMessages activityMessage = new ActivityMessages();
		activityMessage.setActivityId(activityId);
		activityMessage.setCreateUserId(userId);
		activityMessage.setCreateDatetime(new Date());
		activityMessage.setMsg(message);
		
		customActivityMessagesMapper.insert(activityMessage);
	}

	/**
	 * <pre>
	 * 查詢某一個活動的所有留言
	 * </pre>
	 * 
	 * @param activityId
	 * @return
	 */
	public List<ActivityMessagesResp> getActivityMessages(Integer activityId) {
		return customActivityMessagesMapper.selectActivityMessagesRespByActivityId(activityId);
	}
}
