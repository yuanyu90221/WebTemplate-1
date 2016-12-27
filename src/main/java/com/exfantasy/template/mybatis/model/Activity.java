package com.exfantasy.template.mybatis.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

public class Activity {
    @ApiModelProperty(notes = "活動 ID", required = true)
    private Integer activityId;

    @ApiModelProperty(notes = "建立活動的使用者 ID", required = true)
    private Integer createUserId;

    @ApiModelProperty(notes = "建立活動日期", required = true)
    private Date createDate;

    @ApiModelProperty(notes = "活動標題", required = true)
    private String title;

    @ApiModelProperty(notes = "活動描述", required = true)
    private String desc;

    @ApiModelProperty(notes = "活動開始時間", required = true)
    private Date startDatetime;

    @ApiModelProperty(notes = "活動緯度", required = true)
    private BigDecimal latitude;

    @ApiModelProperty(notes = "活動經度", required = true)
    private BigDecimal longitude;

    @ApiModelProperty(notes = "活動預計人數", required = true)
    private Integer attendeeNum;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getAttendeeNum() {
        return attendeeNum;
    }

    public void setAttendeeNum(Integer attendeeNum) {
        this.attendeeNum = attendeeNum;
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
        Activity other = (Activity) that;
        return (this.getActivityId() == null ? other.getActivityId() == null : this.getActivityId().equals(other.getActivityId()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getStartDatetime() == null ? other.getStartDatetime() == null : this.getStartDatetime().equals(other.getStartDatetime()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getAttendeeNum() == null ? other.getAttendeeNum() == null : this.getAttendeeNum().equals(other.getAttendeeNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getActivityId() == null) ? 0 : getActivityId().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getStartDatetime() == null) ? 0 : getStartDatetime().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getAttendeeNum() == null) ? 0 : getAttendeeNum().hashCode());
        return result;
    }
}