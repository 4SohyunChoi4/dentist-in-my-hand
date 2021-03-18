package com.toothfairy.dentist.ask;

public class Ask {

    private boolean replyIsNull;
    private String name;
    private String content;
    private String createTime;
    private String reply;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isReplyIsNull() {
        return replyIsNull;
    }

    public void setReplyIsNull(boolean replyIsNull) {
        this.replyIsNull = replyIsNull;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
