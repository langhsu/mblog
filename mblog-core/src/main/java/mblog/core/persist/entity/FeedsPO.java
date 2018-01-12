/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 动态
 * 
 * @author langhsu
 * 
 */
@Entity
@Table(name = "mto_feeds")
public class FeedsPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "own_id")
	private long ownId; // 所属用户Id

	private int type;

	@Column(name = "post_id")
	private long postId; // 目标Id

	@Column(name = "author_id")
	private long authorId; // 作者
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
