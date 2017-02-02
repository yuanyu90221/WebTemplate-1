package com.exfantasy.template.mybatis.model;

import io.swagger.annotations.ApiModelProperty;

public class ReceiptReward {
    @ApiModelProperty(notes = "期別", required = true)
    private String section;

    @ApiModelProperty(notes = "獎別", required = true)
    private Integer rewardType;

    @ApiModelProperty(notes = "開獎號碼", required = true)
    private String number;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section == null ? null : section.trim();
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
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
        ReceiptReward other = (ReceiptReward) that;
        return (this.getSection() == null ? other.getSection() == null : this.getSection().equals(other.getSection()))
            && (this.getRewardType() == null ? other.getRewardType() == null : this.getRewardType().equals(other.getRewardType()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSection() == null) ? 0 : getSection().hashCode());
        result = prime * result + ((getRewardType() == null) ? 0 : getRewardType().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", section=").append(section);
        sb.append(", rewardType=").append(rewardType);
        sb.append(", number=").append(number);
        sb.append("]");
        return sb.toString();
    }
}