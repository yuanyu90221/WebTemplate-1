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
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
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
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStartDatetime() == null) ? 0 : getStartDatetime().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getAttendeeNum() == null) ? 0 : getAttendeeNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", activityId=").append(activityId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createDate=").append(createDate);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", startDatetime=").append(startDatetime);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", attendeeNum=").append(attendeeNum);
        sb.append("]");
        return sb.toString();
    }
}