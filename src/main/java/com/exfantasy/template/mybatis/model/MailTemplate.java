package com.exfantasy.template.mybatis.model;

import io.swagger.annotations.ApiModelProperty;

public class MailTemplate {
    @ApiModelProperty(notes = "模板 ID", required = true)
    private Integer templateId;

    @ApiModelProperty(notes = "模板類別", required = true)
    private String type;

    @ApiModelProperty(notes = "Mail 主旨", required = true)
    private String subject;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
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
        MailTemplate other = (MailTemplate) that;
        return (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", templateId=").append(templateId);
        sb.append(", type=").append(type);
        sb.append(", subject=").append(subject);
        sb.append("]");
        return sb.toString();
    }
}