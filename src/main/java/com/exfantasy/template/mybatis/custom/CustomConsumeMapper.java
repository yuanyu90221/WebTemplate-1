package com.exfantasy.template.mybatis.custom;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.exfantasy.template.mybatis.mapper.ConsumeMapper;
import com.exfantasy.template.mybatis.model.Consume;

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
}
