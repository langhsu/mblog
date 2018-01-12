package mblog.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author langhsu on 2015/8/31.
 */
public class NotifyEvent extends ApplicationEvent {
	private static final long serialVersionUID = -4261382494171476390L;
	
	private long fromUserId;
    private long toUserId;
    private int event;
    private long postId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public NotifyEvent(Object source) {
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
}
