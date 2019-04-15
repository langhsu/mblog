package com.mtons.mblog.modules.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 通知
 * @author langhsu on 2015/8/31.
 */
@Entity
@Table(name = "mto_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 所属用户Id
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * 消息来源用户Id
     */
    @Column(name = "from_id")
    private long fromId;

    /**
     * 事件类型 {@link com.mtons.mblog.base.lang.Consts#MESSAGE_EVENT_COMMENT}
     */
    private int event; // 事件

    /**
     * 关联文章ID
     */
    @Column(name = "post_id")
    private long postId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    /**
     * 阅读状态 {@link com.mtons.mblog.base.lang.Consts#UNREAD}
     */
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
