package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.ConsumeExample.Criteria;
import com.exfantasy.template.mybatis.model.ConsumeExample.Criterion;
import com.exfantasy.template.mybatis.model.ConsumeExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class ConsumeSqlProvider {

    public String countByExample(ConsumeExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("consume");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(ConsumeExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("consume");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Consume record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("consume");
        
        if (record.getLotteryNo() != null) {
            sql.VALUES("lottery_no", "#{lotteryNo,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=INTEGER}");
        }
        
        if (record.getConsumeDate() != null) {
            sql.VALUES("consume_date", "#{consumeDate,jdbcType=DATE}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }
        
        if (record.getProdName() != null) {
            sql.VALUES("prod_name", "#{prodName,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            sql.VALUES("amount", "#{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getPrize() != null) {
            sql.VALUES("prize", "#{prize,jdbcType=DECIMAL}");
        }
        
        sql.VALUES("got", "#{got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler}");
        
        return sql.toString();
    }

    public String selectByExample(ConsumeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("lottery_no");
        } else {
            sql.SELECT("lottery_no");
        }
        sql.SELECT("user_id");
        sql.SELECT("consume_date");
        sql.SELECT("type");
        sql.SELECT("prod_name");
        sql.SELECT("amount");
        sql.SELECT("prize");
        sql.SELECT("got");
        sql.FROM("consume");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Consume record = (Consume) parameter.get("record");
        ConsumeExample example = (ConsumeExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("consume");
        
        if (record.getLotteryNo() != null) {
            sql.SET("lottery_no = #{record.lotteryNo,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{record.userId,jdbcType=INTEGER}");
        }
        
        if (record.getConsumeDate() != null) {
            sql.SET("consume_date = #{record.consumeDate,jdbcType=DATE}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{record.type,jdbcType=INTEGER}");
        }
        
        if (record.getProdName() != null) {
            sql.SET("prod_name = #{record.prodName,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            sql.SET("amount = #{record.amount,jdbcType=DECIMAL}");
        }
        
        if (record.getPrize() != null) {
            sql.SET("prize = #{record.prize,jdbcType=DECIMAL}");
        }
        
        sql.SET("got = #{record.got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler}");
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("consume");
        
        sql.SET("lottery_no = #{record.lotteryNo,jdbcType=VARCHAR}");
        sql.SET("user_id = #{record.userId,jdbcType=INTEGER}");
        sql.SET("consume_date = #{record.consumeDate,jdbcType=DATE}");
        sql.SET("type = #{record.type,jdbcType=INTEGER}");
        sql.SET("prod_name = #{record.prodName,jdbcType=VARCHAR}");
        sql.SET("amount = #{record.amount,jdbcType=DECIMAL}");
        sql.SET("prize = #{record.prize,jdbcType=DECIMAL}");
        sql.SET("got = #{record.got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler}");
        
        ConsumeExample example = (ConsumeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Consume record) {
        SQL sql = new SQL();
        sql.UPDATE("consume");
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{userId,jdbcType=INTEGER}");
        }
        
        if (record.getConsumeDate() != null) {
            sql.SET("consume_date = #{consumeDate,jdbcType=DATE}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }
        
        if (record.getProdName() != null) {
            sql.SET("prod_name = #{prodName,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            sql.SET("amount = #{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getPrize() != null) {
            sql.SET("prize = #{prize,jdbcType=DECIMAL}");
        }
        
        sql.SET("got = #{got,jdbcType=CHAR,typeHandler=com.exfantasy.template.typehandler.BooleanTypeHandler}");
        
        sql.WHERE("lottery_no = #{lotteryNo,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, ConsumeExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}