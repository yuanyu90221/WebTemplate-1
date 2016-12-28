package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.ActivityMessages;
import com.exfantasy.template.mybatis.model.ActivityMessagesExample;
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
public interface ActivityMessagesMapper {
    @Delete({
        "delete from activity_messages",
        "where msg_id = #{msgId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer msgId);

    @Insert({
        "insert into activity_messages (activity_id, create_user_id, ",
        "create_date, msg)",
        "values (#{activityId,jdbcType=INTEGER}, #{createUserId,jdbcType=INTEGER}, ",
        "#{createDate,jdbcType=DATE}, #{msg,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="msgId", before=false, resultType=Integer.class)
    int insert(ActivityMessages record);

    @InsertProvider(type=ActivityMessagesSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="msgId", before=false, resultType=Integer.class)
    int insertSelective(ActivityMessages record);

    @SelectProvider(type=ActivityMessagesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="msg_id", property="msgId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="activity_id", property="activityId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.DATE),
        @Result(column="msg", property="msg", jdbcType=JdbcType.VARCHAR)
    })
    List<ActivityMessages> selectByExample(ActivityMessagesExample example);

    @Select({
        "select",
        "msg_id, activity_id, create_user_id, create_date, msg",
        "from activity_messages",
        "where msg_id = #{msgId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="msg_id", property="msgId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="activity_id", property="activityId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.DATE),
        @Result(column="msg", property="msg", jdbcType=JdbcType.VARCHAR)
    })
    ActivityMessages selectByPrimaryKey(Integer msgId);

    @UpdateProvider(type=ActivityMessagesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ActivityMessages record);

    @Update({
        "update activity_messages",
        "set activity_id = #{activityId,jdbcType=INTEGER},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "create_date = #{createDate,jdbcType=DATE},",
          "msg = #{msg,jdbcType=VARCHAR}",
        "where msg_id = #{msgId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ActivityMessages record);
}