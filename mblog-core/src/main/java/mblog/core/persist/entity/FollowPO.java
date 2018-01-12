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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * 我的关注
 * 
 * @author langhsu
 * 
 */
@Entity
@Table(name = "mto_follows")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class FollowPO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * 所属用户Id
	 */
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserPO user;

	/**
	 * 关注用户Id
	 */
	@ManyToOne
	@JoinColumn(name = "follow_id", nullable = false)
	private UserPO follow;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserPO getUser() {
		return user;
	}

	public void setUser(UserPO user) {
		this.user = user;
	}

	public UserPO getFollow() {
		return follow;
	}

	public void setFollow(UserPO follow) {
		this.follow = follow;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
