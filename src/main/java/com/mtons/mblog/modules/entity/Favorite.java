package com.mtons.mblog.modules.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 喜欢/收藏
 * @author langhsu on 2015/8/31.
 */
@Entity
@Table(name = "mto_favorite", indexes = {
        @Index(name = "IK_USER_ID", columnList = "user_id")
})
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 所属用户
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * 内容ID
     */
    @Column(name = "post_id")
    private long postId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

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
}
