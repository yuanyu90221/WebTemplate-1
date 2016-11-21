package com.exfantasy.template.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ConsumeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConsumeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> gotCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            gotCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getGotCriteria() {
            return gotCriteria;
        }

        protected void addGotCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            gotCriteria.add(new Criterion(condition, value, "com.exfantasy.template.typehandler.BooleanTypeHandler"));
            allCriteria = null;
        }

        protected void addGotCriterion(String condition, boolean value1, boolean value2, String property) {
            gotCriteria.add(new Criterion(condition, value1, value2, "com.exfantasy.template.typehandler.BooleanTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || gotCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(gotCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andConsumeIdIsNull() {
            addCriterion("consume_id is null");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIsNotNull() {
            addCriterion("consume_id is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeIdEqualTo(Integer value) {
            addCriterion("consume_id =", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotEqualTo(Integer value) {
            addCriterion("consume_id <>", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdGreaterThan(Integer value) {
            addCriterion("consume_id >", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("consume_id >=", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLessThan(Integer value) {
            addCriterion("consume_id <", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLessThanOrEqualTo(Integer value) {
            addCriterion("consume_id <=", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIn(List<Integer> values) {
            addCriterion("consume_id in", values, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotIn(List<Integer> values) {
            addCriterion("consume_id not in", values, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdBetween(Integer value1, Integer value2) {
            addCriterion("consume_id between", value1, value2, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("consume_id not between", value1, value2, "consumeId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andConsumeDateIsNull() {
            addCriterion("consume_date is null");
            return (Criteria) this;
        }

        public Criteria andConsumeDateIsNotNull() {
            addCriterion("consume_date is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeDateEqualTo(Date value) {
            addCriterionForJDBCDate("consume_date =", value, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("consume_date <>", value, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateGreaterThan(Date value) {
            addCriterionForJDBCDate("consume_date >", value, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("consume_date >=", value, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateLessThan(Date value) {
            addCriterionForJDBCDate("consume_date <", value, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("consume_date <=", value, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateIn(List<Date> values) {
            addCriterionForJDBCDate("consume_date in", values, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("consume_date not in", values, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("consume_date between", value1, value2, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andConsumeDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("consume_date not between", value1, value2, "consumeDate");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Boolean value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Boolean value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Boolean value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Boolean value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Boolean> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Boolean> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andProdNameIsNull() {
            addCriterion("prod_name is null");
            return (Criteria) this;
        }

        public Criteria andProdNameIsNotNull() {
            addCriterion("prod_name is not null");
            return (Criteria) this;
        }

        public Criteria andProdNameEqualTo(String value) {
            addCriterion("prod_name =", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotEqualTo(String value) {
            addCriterion("prod_name <>", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameGreaterThan(String value) {
            addCriterion("prod_name >", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameGreaterThanOrEqualTo(String value) {
            addCriterion("prod_name >=", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLessThan(String value) {
            addCriterion("prod_name <", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLessThanOrEqualTo(String value) {
            addCriterion("prod_name <=", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLike(String value) {
            addCriterion("prod_name like", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotLike(String value) {
            addCriterion("prod_name not like", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameIn(List<String> values) {
            addCriterion("prod_name in", values, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotIn(List<String> values) {
            addCriterion("prod_name not in", values, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameBetween(String value1, String value2) {
            addCriterion("prod_name between", value1, value2, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotBetween(String value1, String value2) {
            addCriterion("prod_name not between", value1, value2, "prodName");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Long value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Long value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Long value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Long value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Long value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Long> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Long> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Long value1, Long value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Long value1, Long value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andLotteryNoIsNull() {
            addCriterion("lottery_no is null");
            return (Criteria) this;
        }

        public Criteria andLotteryNoIsNotNull() {
            addCriterion("lottery_no is not null");
            return (Criteria) this;
        }

        public Criteria andLotteryNoEqualTo(String value) {
            addCriterion("lottery_no =", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoNotEqualTo(String value) {
            addCriterion("lottery_no <>", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoGreaterThan(String value) {
            addCriterion("lottery_no >", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoGreaterThanOrEqualTo(String value) {
            addCriterion("lottery_no >=", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoLessThan(String value) {
            addCriterion("lottery_no <", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoLessThanOrEqualTo(String value) {
            addCriterion("lottery_no <=", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoLike(String value) {
            addCriterion("lottery_no like", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoNotLike(String value) {
            addCriterion("lottery_no not like", value, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoIn(List<String> values) {
            addCriterion("lottery_no in", values, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoNotIn(List<String> values) {
            addCriterion("lottery_no not in", values, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoBetween(String value1, String value2) {
            addCriterion("lottery_no between", value1, value2, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andLotteryNoNotBetween(String value1, String value2) {
            addCriterion("lottery_no not between", value1, value2, "lotteryNo");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNull() {
            addCriterion("prize is null");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNotNull() {
            addCriterion("prize is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeEqualTo(Long value) {
            addCriterion("prize =", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotEqualTo(Long value) {
            addCriterion("prize <>", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThan(Long value) {
            addCriterion("prize >", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThanOrEqualTo(Long value) {
            addCriterion("prize >=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThan(Long value) {
            addCriterion("prize <", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThanOrEqualTo(Long value) {
            addCriterion("prize <=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeIn(List<Long> values) {
            addCriterion("prize in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotIn(List<Long> values) {
            addCriterion("prize not in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeBetween(Long value1, Long value2) {
            addCriterion("prize between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotBetween(Long value1, Long value2) {
            addCriterion("prize not between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andGotIsNull() {
            addCriterion("got is null");
            return (Criteria) this;
        }

        public Criteria andGotIsNotNull() {
            addCriterion("got is not null");
            return (Criteria) this;
        }

        public Criteria andGotEqualTo(boolean value) {
            addGotCriterion("got =", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotNotEqualTo(boolean value) {
            addGotCriterion("got <>", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotGreaterThan(boolean value) {
            addGotCriterion("got >", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotGreaterThanOrEqualTo(boolean value) {
            addGotCriterion("got >=", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotLessThan(boolean value) {
            addGotCriterion("got <", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotLessThanOrEqualTo(boolean value) {
            addGotCriterion("got <=", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotLike(boolean value) {
            addGotCriterion("got like", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotNotLike(boolean value) {
            addGotCriterion("got not like", value, "got");
            return (Criteria) this;
        }

        public Criteria andGotIn(List<Boolean> values) {
            addGotCriterion("got in", values, "got");
            return (Criteria) this;
        }

        public Criteria andGotNotIn(List<Boolean> values) {
            addGotCriterion("got not in", values, "got");
            return (Criteria) this;
        }

        public Criteria andGotBetween(boolean value1, boolean value2) {
            addGotCriterion("got between", value1, value2, "got");
            return (Criteria) this;
        }

        public Criteria andGotNotBetween(boolean value1, boolean value2) {
            addGotCriterion("got not between", value1, value2, "got");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}