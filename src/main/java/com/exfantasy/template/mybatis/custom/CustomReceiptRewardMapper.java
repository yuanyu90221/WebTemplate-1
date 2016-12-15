package com.exfantasy.template.mybatis.custom;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.exfantasy.template.mybatis.mapper.ReceiptRewardMapper;
import com.exfantasy.template.mybatis.model.ReceiptReward;

@Mapper
public interface CustomReceiptRewardMapper extends ReceiptRewardMapper {
	/**
	 * <pre>
	 * 根據傳入期號刪除中獎號碼
	 * 
	 * PS: 這個自己加的, 因為 Table: receipt_reward 沒有 PKey, 所以 generator 不會產生
	 * </pre>
	 * 
	 * @param section
	 * @return
	 */
	@Delete({
    	"delete from receipt_reward",
    	"where section = #{section,jdbcType=CHAR}"
    })
    int deleteBySection(String section);
	
	/**
	 * <pre>
	 * 根據傳入期號批次刪除中獎號碼
	 * <a href="http://www.mybatis.org/mybatis-3/dynamic-sql.html">http://www.mybatis.org/mybatis-3/dynamic-sql.html</a>
	 * </pre>
	 * 
	 * @param sections
	 * @return
	 */
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
	
	/**
	 * <pre>
	 * 批次新增中獎資料
	 * <a href="http://stackoverflow.com/questions/23486547/mybatis-batch-insert-update-for-oracle">http://stackoverflow.com/questions/23486547/mybatis-batch-insert-update-for-oracle</a>
	 * </pre>
	 * 
	 * @param receiptRewards
	 * @return
	 */
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
