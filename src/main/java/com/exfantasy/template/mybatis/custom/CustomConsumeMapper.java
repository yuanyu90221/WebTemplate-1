package com.exfantasy.template.mybatis.custom;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;

import com.exfantasy.template.mybatis.mapper.ConsumeMapper;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.typehandler.BooleanTypeHandler;

@Mapper
public interface CustomConsumeMapper extends ConsumeMapper {
	/**
	 * <pre>
	 * 批次更新消費資料 table 的中獎欄位
	 * <a href="http://stackoverflow.com/questions/10797794/multiple-queries-executed-in-java-in-single-statement">http://stackoverflow.com/questions/10797794/multiple-queries-executed-in-java-in-single-statement</a>
	 * </pre>
	 * 
	 * @param consumesToUpdateGot
	 * @return
	 */
	@Update({
		"<script>",
		"<foreach collection='list' item='consume' separator=';'>",
		"update consume ",
		"set got = #{consume.got,jdbcType=INTEGER} ",
		"where lottery_no = #{consume.lotteryNo,jdbcType=VARCHAR}",
		"</foreach>",
		"</script>"
	})
	int batchUpdateGot(List<Consume> consumesToUpdateGot);
	
	/**
	 * <pre>
	 * 批次更新消費資料 table 的是否已寄信欄位
	 * </pre>
	 * 
	 * @param consumesToUpdateAlreadySent
	 * @return
	 */
	@Update({
		"<script>",
		"<foreach collection='list' item='consume' separator=';'>",
		"update consume ",
		"set already_sent = #{consume.alreadySent,jdbcType=CHAR,typeHandler=com.exfantasy.template.mybatis.typehandler.BooleanTypeHandler} ",
		"where lottery_no = #{consume.lotteryNo,jdbcType=VARCHAR}",
		"</foreach>",
		"</script>"
	})
	int batchUpdateAlreadtSent(List<Consume> consumesToUpdateAlreadySent);
	
	@Delete({
		"<script>",
		"<foreach collection='list' item='lotteryNo' separator=';'>",
		"delete from consume ",
		"where lottery_no = #{lotteryNo,jdbcType=VARCHAR}",
		"</foreach>",
		"</script>"
	})
	int batchDeleteByLotteryNo(List<String> lotteryNos);
	
	@Select(
		value = "{" + 
					"call FindConsumesByUid(" + 
						"#{uid, mode=IN, jdbcType=INTEGER}" + 
					")" + 
				"}"
	)
	@Options(statementType = StatementType.CALLABLE) 
	@ResultType(Consume.class)
	@Results({
        @Result(column="lottery_no", property="lotteryNo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="consume_date", property="consumeDate", jdbcType=JdbcType.DATE),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="prize", property="prize", jdbcType=JdbcType.DECIMAL),
        @Result(column="got", property="got", jdbcType=JdbcType.INTEGER),
        @Result(column="already_sent", property="alreadySent", typeHandler=BooleanTypeHandler.class, jdbcType=JdbcType.CHAR)
    })
	List<Consume> findConsumesByUid(@Param("uid") Integer uid);
	
	@Select(
		value = "{" + 
					"call FindConsumesByLotteryNo(" + 
						"#{lotteryNo, mode=IN, jdbcType=VARCHAR}" + 
					")" + 
				"}"
	)
	@Options(statementType = StatementType.CALLABLE) 
	@ResultType(Consume.class)
	@Results({
        @Result(column="lottery_no", property="lotteryNo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="consume_date", property="consumeDate", jdbcType=JdbcType.DATE),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="prize", property="prize", jdbcType=JdbcType.DECIMAL),
        @Result(column="got", property="got", jdbcType=JdbcType.INTEGER),
        @Result(column="already_sent", property="alreadySent", typeHandler=BooleanTypeHandler.class, jdbcType=JdbcType.CHAR)
    })
	Consume findConsumeByLotteryNo(@Param("lotteryNo") String lotteryNo);
}
