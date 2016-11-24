package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.ReceiptReward;
import com.exfantasy.template.mybatis.model.ReceiptRewardExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ReceiptRewardMapper {
	// 這個自己加的, 因為 Table: receipt_reward 沒有 PKey, 所以 generator 不會產生
	@Delete({
    	"delete from receipt_reward",
    	"where section = #{section,jdbcType=CHAR}"
    })
    int deleteBySection(String section);
	
    @SelectProvider(type=ReceiptRewardSqlProvider.class, method="countByExample")
    int countByExample(ReceiptRewardExample example);

    @DeleteProvider(type=ReceiptRewardSqlProvider.class, method="deleteByExample")
    int deleteByExample(ReceiptRewardExample example);

    @Insert({
        "insert into receipt_reward (section, reward_type, ",
        "number)",
        "values (#{section,jdbcType=CHAR}, #{rewardType,jdbcType=INTEGER}, ",
        "#{number,jdbcType=CHAR})"
    })
    int insert(ReceiptReward record);

    @InsertProvider(type=ReceiptRewardSqlProvider.class, method="insertSelective")
    int insertSelective(ReceiptReward record);

    @SelectProvider(type=ReceiptRewardSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="section", property="section", jdbcType=JdbcType.CHAR),
        @Result(column="reward_type", property="rewardType", jdbcType=JdbcType.INTEGER),
        @Result(column="number", property="number", jdbcType=JdbcType.CHAR)
    })
    List<ReceiptReward> selectByExample(ReceiptRewardExample example);

    @UpdateProvider(type=ReceiptRewardSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ReceiptReward record, @Param("example") ReceiptRewardExample example);

    @UpdateProvider(type=ReceiptRewardSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ReceiptReward record, @Param("example") ReceiptRewardExample example);
}