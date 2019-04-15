package com.mtons.mblog.modules.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author langhsu on 2015/8/31.
 */
public class MessageEvent extends ApplicationEvent {
	private static final long serialVersionUID = -4261382494171476390L;

    /**
     * 消息生产者Id
     */
	private long fromUserId;

    /**
     * 消息消费者Id
     */
    private long toUserId;

    /**
     * 消息类型
     */
    private int event;

    /**
     * 相关文章Id
     */
    private long postId;

    /**
     * 如果是回复评论 回复的评论Id
     */
    private long commentParentId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public MessageEvent(Object source) {
        super(source);
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
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

    public long getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(long commentParentId) {
        this.commentParentId = commentParentId;
    }
}
