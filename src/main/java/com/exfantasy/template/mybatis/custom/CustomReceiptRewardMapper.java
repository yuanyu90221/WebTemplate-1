package com.exfantasy.template.mybatis.custom;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.exfantasy.template.mybatis.mapper.ReceiptRewardMapper;
import com.exfantasy.template.mybatis.model.ReceiptReward;

@Mapper
public interface CustomReceiptRewardMapper extends ReceiptRewardMapper {
	// 這個自己加的, 因為 Table: receipt_reward 沒有 PKey, 所以 generator 不會產生
	@Delete({
    	"delete from receipt_reward",
    	"where section = #{section,jdbcType=CHAR}"
    })
    int deleteBySection(String section);
	
	// 這個自己加的, batch delete
	// 參考: http://www.mybatis.org/mybatis-3/dynamic-sql.html
	@Delete({
		"<script>",
		"delete from receipt_reward",
		"where section in",
		"<foreach collection='list' item='section' open='(' separator=',' close=')'>",
		"#{section,jdbcType=CHAR}",
		"</foreach>",
		"</script>"
	})
	int batchDeleteBySection(List<String> sections);
	
	// 這個自己加的, batch insert
	// 參考: http://stackoverflow.com/questions/23486547/mybatis-batch-insert-update-for-oracle
	@Insert({
		"<script>",
		"insert into receipt_reward (section, reward_type, number) values",
		"<foreach collection='list' item='record' separator=','>",
		"(",
		"#{record.section,jdbcType=CHAR}, #{record.rewardType,jdbcType=INTEGER}, #{record.number,jdbcType=CHAR}",
		")",
		"</foreach>",
		"</script>"
	})
	int batchInsert(List<ReceiptReward> receiptRewards);
}
