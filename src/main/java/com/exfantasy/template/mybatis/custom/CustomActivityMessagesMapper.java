package com.exfantasy.template.mybatis.custom;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.exfantasy.template.mybatis.mapper.ActivityMessagesMapper;
import com.exfantasy.template.vo.response.ActivityMessagesResp;

@Mapper
public interface CustomActivityMessagesMapper extends ActivityMessagesMapper {
	@Select({
        "select",
        "u.email as create_user_mail, am.create_datetime as create_datetime, am.msg as msg",
        "from activity_messages am",
        "left join user u on am.create_user_id = u.user_id",
        "where am.activity_id = #{activityId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="create_user_mail", property="createUserEmail", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="msg", property="msg", jdbcType=JdbcType.VARCHAR)
    })
	List<ActivityMessagesResp> selectActivityMessagesRespByActivityId(Integer activityId);
	
}
