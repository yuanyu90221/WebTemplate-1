package com.exfantasy.template.mybatis.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class Consume {
    @ApiModelProperty(notes = "發票號碼", required = true)
    private String lotteryNo;

    @ApiModelProperty(notes = "使用者 ID", required = true)
    private Integer userId;

    @ApiModelProperty(notes = "消費日期", required = true)
    private Date consumeDate;

    @ApiModelProperty(notes = "分類", required = true)
    private Integer type;

    @ApiModelProperty(notes = "商品名稱", required = true)
    private String prodName;

    @ApiModelProperty(notes = "消費金額", required = true)
    private Long amount;

    @ApiModelProperty(notes = "中獎金額", required = true)
    private Long prize;

    @ApiModelProperty(notes = "是否中獎", required = true)
    private Integer got;

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo == null ? null : lotteryNo.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getPrize() {
        return prize;
    }

    public void setPrize(Long prize) {
        this.prize = prize;
    }

    public Integer getGot() {
        return got;
    }

    public void setGot(Integer got) {
        this.got = got;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Consume other = (Consume) that;
        return (this.getLotteryNo() == null ? other.getLotteryNo() == null : this.getLotteryNo().equals(other.getLotteryNo()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getConsumeDate() == null ? other.getConsumeDate() == null : this.getConsumeDate().equals(other.getConsumeDate()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getProdName() == null ? other.getProdName() == null : this.getProdName().equals(other.getProdName()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getPrize() == null ? other.getPrize() == null : this.getPrize().equals(other.getPrize()))
            && (this.getGot() == null ? other.getGot() == null : this.getGot().equals(other.getGot()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLotteryNo() == null) ? 0 : getLotteryNo().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getConsumeDate() == null) ? 0 : getConsumeDate().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getProdName() == null) ? 0 : getProdName().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getPrize() == null) ? 0 : getPrize().hashCode());
        result = prime * result + ((getGot() == null) ? 0 : getGot().hashCode());
        return result;
    }
}