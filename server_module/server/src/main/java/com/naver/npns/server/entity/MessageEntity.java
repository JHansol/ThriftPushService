package com.naver.npns.server.entity;

import javax.persistence.*;

@Entity
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="message_idx")
    private Long idx;

    @Column(name="message_seq")
    private Long seq;

    @Column(name="message_title")
    private String title;

    @Column(name="message_content")
    private String content;

    @Column(name="message_send_time")
    private Long sendTime;

    @Column(name="user_uuid")
    private String userUUID;

    public MessageEntity(){}

    public MessageEntity(Long seq, String title, String content, Long sendTime, String userUUID) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.sendTime = sendTime;
        this.userUUID = userUUID;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "idx=" + idx +
                ", seq=" + seq +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", userUUID='" + userUUID + '\'' +
                '}';
    }
}
