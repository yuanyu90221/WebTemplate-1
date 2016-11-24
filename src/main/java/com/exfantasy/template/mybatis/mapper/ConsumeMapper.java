package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.ConsumeExample;
import com.exfantasy.template.typehandler.BooleanTypeHandler;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ConsumeMapper {
    @SelectProvider(type=ConsumeSqlProvider.class, method="countByExample")
    int countByExample(ConsumeExample example);

    @DeleteProvider(type=ConsumeSqlProvider.class, method="deleteByExample")
    int deleteByExample(ConsumeExample example);

    @Delete({
        "delete from consume",
        "where lottery_no = #{lotteryNo,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String lotteryNo);

    @Insert({
        "insert into consume (lottery_no, user_id, ",
        "consume_date, type, ",
        "prod_name, amount, ",
        "prize, got)",
        "values (#{lotteryNo,jdbcType=VARCHAR}, #{userId,jdbcType=INTEGER}, ",
        "#{consumeDate,jdbcType=DATE}, #{type,jdbcType=INTEGER}, ",
        "#{prodName,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{prize,jdbcType=DECIMAL}, #{got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler})"
    })
    int insert(Consume record);

    @InsertProvider(type=ConsumeSqlProvider.class, method="insertSelective")
    int insertSelective(Consume record);

    @SelectProvider(type=ConsumeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="lottery_no", property="lotteryNo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="consume_date", property="consumeDate", jdbcType=JdbcType.DATE),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="prize", property="prize", jdbcType=JdbcType.DECIMAL),
        @Result(column="got", property="got", typeHandler=BooleanTypeHandler.class, jdbcType=JdbcType.CHAR)
    })
    List<Consume> selectByExample(ConsumeExample example);

    @Select({
        "select",
        "lottery_no, user_id, consume_date, type, prod_name, amount, prize, got",
        "from consume",
        "where lottery_no = #{lotteryNo,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="lottery_no", property="lotteryNo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="consume_date", property="consumeDate", jdbcType=JdbcType.DATE),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="prize", property="prize", jdbcType=JdbcType.DECIMAL),
        @Result(column="got", property="got", typeHandler=BooleanTypeHandler.class, jdbcType=JdbcType.CHAR)
    })
    Consume selectByPrimaryKey(String lotteryNo);

    @UpdateProvider(type=ConsumeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Consume record, @Param("example") ConsumeExample example);

    @UpdateProvider(type=ConsumeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Consume record, @Param("example") ConsumeExample example);

    @UpdateProvider(type=ConsumeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Consume record);

    @Update({
        "update consume",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "consume_date = #{consumeDate,jdbcType=DATE},",
          "type = #{type,jdbcType=INTEGER},",
          "prod_name = #{prodName,jdbcType=VARCHAR},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "prize = #{prize,jdbcType=DECIMAL},",
          "got = #{got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler}",
        "where lottery_no = #{lotteryNo,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Consume record);
}