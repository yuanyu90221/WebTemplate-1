package com.exfantasy.template.mybatis.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class ActivityMessages {
    @ApiModelProperty(notes = "留言 ID", required = true)
    private Integer msgId;

    @ApiModelProperty(notes = "活動 ID", required = true)
    private Integer activityId;

    @ApiModelProperty(notes = "建立留言的使用者 ID", required = true)
    private Integer createUserId;

    @ApiModelProperty(notes = "建立留言的日期時間", required = true)
    private Date createDatetime;

    @ApiModelProperty(notes = "留言", required = true)
    private String msg;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

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

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
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
        ActivityMessages other = (ActivityMessages) that;
        return (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getActivityId() == null ? other.getActivityId() == null : this.getActivityId().equals(other.getActivityId()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateDatetime() == null ? other.getCreateDatetime() == null : this.getCreateDatetime().equals(other.getCreateDatetime()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getActivityId() == null) ? 0 : getActivityId().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateDatetime() == null) ? 0 : getCreateDatetime().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", msgId=").append(msgId);
        sb.append(", activityId=").append(activityId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createDatetime=").append(createDatetime);
        sb.append(", msg=").append(msg);
        sb.append("]");
        return sb.toString();
    }
}