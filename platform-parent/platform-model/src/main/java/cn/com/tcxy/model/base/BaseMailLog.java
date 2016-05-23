package cn.com.tcxy.model.base;

import java.util.Date;

public class BaseMailLog {
    private Long sid;

    private String mailFrom;

    private String mailTo;

    private String subject;

    private String content;

    private String category;

    private Date sendToMqTimestamp;

    private Date sendToUserTimestamp;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom == null ? null : mailFrom.trim();
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo == null ? null : mailTo.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Date getSendToMqTimestamp() {
        return sendToMqTimestamp;
    }

    public void setSendToMqTimestamp(Date sendToMqTimestamp) {
        this.sendToMqTimestamp = sendToMqTimestamp;
    }

    public Date getSendToUserTimestamp() {
        return sendToUserTimestamp;
    }

    public void setSendToUserTimestamp(Date sendToUserTimestamp) {
        this.sendToUserTimestamp = sendToUserTimestamp;
    }
}