package com.exfantasy.template.mybatis.custom;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.exfantasy.template.mybatis.mapper.ConsumeMapper;
import com.exfantasy.template.mybatis.model.Consume;

@Mapper
public interface CustomConsumeMapper extends ConsumeMapper {
	// 這個自己加的, batch update
	// 參考: http://stackoverflow.com/questions/10797794/multiple-queries-executed-in-java-in-single-statement
	@Update({
		"<script>",
		"<foreach collection='list' item='consume'>",
		"update consume ",
		"set got = #{consume.got,jdbcType=INTEGER} ",
		"where lottery_no = #{consume.lotteryNo,jdbcType=VARCHAR};",
		"</foreach>",
		"</script>"
	})
	int batchUpdate(List<Consume> consumesToUpdateGot);
}
