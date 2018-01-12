package mblog.core.persist.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 通知
 * @author langhsu on 2015/8/31.
 */
@Entity
@Table(name = "mto_notify")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotifyPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "own_id")
    private long ownId;

    @Column(name = "from_id")
    private long fromId;

    private int event; // 事件

    @Column(name = "post_id")
    private long postId; // 关联文章ID

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private int status; // 阅读状态

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnId() {
        return ownId;
    }

    public void setOwnId(long ownId) {
        this.ownId = ownId;
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
