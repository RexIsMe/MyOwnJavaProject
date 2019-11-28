package com.atguigu.hbase.weibo;

public class Message {
    private String uid;
    private String timestamp;
    private String content;

    @Override
    public String toString() {
        return "Message{" +
                "uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
