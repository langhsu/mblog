/**
 * 
 */
package mblog.core.pojos;

import java.io.Serializable;

/**
 * @author langhsu
 * 
 */
public class Tag implements Serializable {
	private static final long serialVersionUID = 3262289824211326798L;

	private long id;
	private String name;
	private long lastPostId;
	private int featured; // 是否推荐
	private int posts;
	private int hots;
	
	private Post post;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosts() {
		return posts;
	}

	public void setPosts(int posts) {
		this.posts = posts;
	}

	public int getHots() {
		return hots;
	}

	public void setHots(int hots) {
		this.hots = hots;
	}

	public long getLastPostId() {
		return lastPostId;
	}

	public void setLastPostId(long lastPostId) {
		this.lastPostId = lastPostId;
	}

	public int getFeatured() {
		return featured;
	}

	public void setFeatured(int featured) {
		this.featured = featured;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
