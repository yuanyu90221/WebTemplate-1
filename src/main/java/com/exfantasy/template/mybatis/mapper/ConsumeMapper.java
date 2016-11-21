package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.ConsumeExample;
import com.exfantasy.template.typehandler.BooleanTypeHandler;
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
public interface ConsumeMapper {
    @Delete({
        "delete from consume",
        "where consume_id = #{consumeId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer consumeId);

    @Insert({
        "insert into consume (user_id, consume_date, ",
        "type, prod_name, ",
        "amount, lottery_no, ",
        "prize, got)",
        "values (#{userId,jdbcType=INTEGER}, #{consumeDate,jdbcType=DATE}, ",
        "#{type,jdbcType=INTEGER}, #{prodName,jdbcType=VARCHAR}, ",
        "#{amount,jdbcType=DECIMAL}, #{lotteryNo,jdbcType=VARCHAR}, ",
        "#{prize,jdbcType=DECIMAL}, #{got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="consumeId", before=false, resultType=Integer.class)
    int insert(Consume record);

    @InsertProvider(type=ConsumeSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="consumeId", before=false, resultType=Integer.class)
    int insertSelective(Consume record);

    @SelectProvider(type=ConsumeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="consume_id", property="consumeId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="consume_date", property="consumeDate", jdbcType=JdbcType.DATE),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="lottery_no", property="lotteryNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="prize", property="prize", jdbcType=JdbcType.DECIMAL),
        @Result(column="got", property="got", typeHandler=BooleanTypeHandler.class, jdbcType=JdbcType.CHAR)
    })
    List<Consume> selectByExample(ConsumeExample example);

    @Select({
        "select",
        "consume_id, user_id, consume_date, type, prod_name, amount, lottery_no, prize, ",
        "got",
        "from consume",
        "where consume_id = #{consumeId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="consume_id", property="consumeId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="consume_date", property="consumeDate", jdbcType=JdbcType.DATE),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="lottery_no", property="lotteryNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="prize", property="prize", jdbcType=JdbcType.DECIMAL),
        @Result(column="got", property="got", typeHandler=BooleanTypeHandler.class, jdbcType=JdbcType.CHAR)
    })
    Consume selectByPrimaryKey(Integer consumeId);

    @UpdateProvider(type=ConsumeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Consume record);

    @Update({
        "update consume",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "consume_date = #{consumeDate,jdbcType=DATE},",
          "type = #{type,jdbcType=INTEGER},",
          "prod_name = #{prodName,jdbcType=VARCHAR},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "lottery_no = #{lotteryNo,jdbcType=VARCHAR},",
          "prize = #{prize,jdbcType=DECIMAL},",
          "got = #{got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler}",
        "where consume_id = #{consumeId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Consume record);
}