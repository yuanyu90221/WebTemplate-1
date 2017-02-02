package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.Activity;
import com.exfantasy.template.mybatis.model.ActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ActivityMapper {
    @Delete({
        "delete from activities",
        "where activity_id = #{activityId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer activityId);

    @Insert({
        "insert into activities (create_user_id, create_date, ",
        "title, description, ",
        "start_datetime, latitude, ",
        "longitude, attendee_num)",
        "values (#{createUserId,jdbcType=INTEGER}, #{createDate,jdbcType=DATE}, ",
        "#{title,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
        "#{startDatetime,jdbcType=TIMESTAMP}, #{latitude,jdbcType=DECIMAL}, ",
        "#{longitude,jdbcType=DECIMAL}, #{attendeeNum,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="activityId", before=false, resultType=Integer.class)
    int insert(Activity record);

    @InsertProvider(type=ActivitySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="activityId", before=false, resultType=Integer.class)
    int insertSelective(Activity record);

    @SelectProvider(type=ActivitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="activity_id", property="activityId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.DATE),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_datetime", property="startDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.DECIMAL),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.DECIMAL),
        @Result(column="attendee_num", property="attendeeNum", jdbcType=JdbcType.DECIMAL)
    })
    List<Activity> selectByExample(ActivityExample example);

    @Select({
        "select",
        "activity_id, create_user_id, create_date, title, description, start_datetime, ",
        "latitude, longitude, attendee_num",
        "from activities",
        "where activity_id = #{activityId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="activity_id", property="activityId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.DATE),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_datetime", property="startDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.DECIMAL),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.DECIMAL),
        @Result(column="attendee_num", property="attendeeNum", jdbcType=JdbcType.DECIMAL)
    })
    Activity selectByPrimaryKey(Integer activityId);

    @UpdateProvider(type=ActivitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Activity record);

    @Update({
        "update activities",
        "set create_user_id = #{createUserId,jdbcType=INTEGER},",
          "create_date = #{createDate,jdbcType=DATE},",
          "title = #{title,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "start_datetime = #{startDatetime,jdbcType=TIMESTAMP},",
          "latitude = #{latitude,jdbcType=DECIMAL},",
          "longitude = #{longitude,jdbcType=DECIMAL},",
          "attendee_num = #{attendeeNum,jdbcType=DECIMAL}",
        "where activity_id = #{activityId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Activity record);
}