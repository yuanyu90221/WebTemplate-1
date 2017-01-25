package com.exfantasy.template.mybatis.model;

import io.swagger.annotations.ApiModelProperty;

public class MailTemplateWithBLOBs extends MailTemplate {
    @ApiModelProperty(notes = "模板 Header", required = true)
    private String header;

    @ApiModelProperty(notes = "內容 Header", required = true)
    private String bodyHeader;

    @ApiModelProperty(notes = "內容 Tail", required = true)
    private String bodyTail;

    @ApiModelProperty(notes = "模板 Tail", required = true)
    private String tail;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header == null ? null : header.trim();
    }

    public String getBodyHeader() {
        return bodyHeader;
    }

    public void setBodyHeader(String bodyHeader) {
        this.bodyHeader = bodyHeader == null ? null : bodyHeader.trim();
    }

    public String getBodyTail() {
        return bodyTail;
    }

    public void setBodyTail(String bodyTail) {
        this.bodyTail = bodyTail == null ? null : bodyTail.trim();
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail == null ? null : tail.trim();
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
        MailTemplateWithBLOBs other = (MailTemplateWithBLOBs) that;
        return (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getHeader() == null ? other.getHeader() == null : this.getHeader().equals(other.getHeader()))
            && (this.getBodyHeader() == null ? other.getBodyHeader() == null : this.getBodyHeader().equals(other.getBodyHeader()))
            && (this.getBodyTail() == null ? other.getBodyTail() == null : this.getBodyTail().equals(other.getBodyTail()))
            && (this.getTail() == null ? other.getTail() == null : this.getTail().equals(other.getTail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getHeader() == null) ? 0 : getHeader().hashCode());
        result = prime * result + ((getBodyHeader() == null) ? 0 : getBodyHeader().hashCode());
        result = prime * result + ((getBodyTail() == null) ? 0 : getBodyTail().hashCode());
        result = prime * result + ((getTail() == null) ? 0 : getTail().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", header=").append(header);
        sb.append(", bodyHeader=").append(bodyHeader);
        sb.append(", bodyTail=").append(bodyTail);
        sb.append(", tail=").append(tail);
        sb.append("]");
        return sb.toString();
    }
}