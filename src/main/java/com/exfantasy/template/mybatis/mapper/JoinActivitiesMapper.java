package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.JoinActivitiesExample;
import com.exfantasy.template.mybatis.model.JoinActivitiesKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface JoinActivitiesMapper {
    @Delete({
        "delete from join_activities",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and activity_id = #{activityId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(JoinActivitiesKey key);

    @Insert({
        "insert into join_activities (user_id, activity_id)",
        "values (#{userId,jdbcType=INTEGER}, #{activityId,jdbcType=INTEGER})"
    })
    int insert(JoinActivitiesKey record);

    @InsertProvider(type=JoinActivitiesSqlProvider.class, method="insertSelective")
    int insertSelective(JoinActivitiesKey record);

    @SelectProvider(type=JoinActivitiesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="activity_id", property="activityId", jdbcType=JdbcType.INTEGER, id=true)
    })
    List<JoinActivitiesKey> selectByExample(JoinActivitiesExample example);
}