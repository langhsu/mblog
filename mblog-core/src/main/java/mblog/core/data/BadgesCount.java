package mblog.core.data;

import java.io.Serializable;

/**
 * @author langhsu on 2015/8/31.
 */
public class BadgesCount implements Serializable {
    private static final long serialVersionUID = 8276459939240769498L;

    private int notifies; // 通知数量
    private int messages; // 私信数量

    public int getNotifies() {
        return notifies;
    }

    public void setNotifies(int notifies) {
        this.notifies = notifies;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }
}
