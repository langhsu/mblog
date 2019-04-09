package com.mtons.mblog.modules.event;

import org.springframework.context.ApplicationEvent;

/**
 *   文章发布事件
 * //合并文章事件, 下层多个订阅者
 * - 推送Feed给粉丝
 * - 文章发布者文章数统计
 * - 推送通知
 *
 * created by langhsu at 2018/05/30
 */
public class PostUpdateEvent extends ApplicationEvent {
    public final static int ACTION_PUBLISH = 1;
    public final static int ACTION_DELETE = 2;

    private long postId;
    private long userId;
    private int action = ACTION_PUBLISH;

    public PostUpdateEvent(Object source) {
        super(source);
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
