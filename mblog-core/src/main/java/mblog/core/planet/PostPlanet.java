/**
 * 
 */
package mblog.core.planet;

import java.util.List;

import mblog.core.pojos.Post;
import mtons.modules.pojos.Page;

/**
 * @author langhsu
 *
 */
public interface PostPlanet {
	Page paging(Page page);
	Page pagingByUserId(Page page, long uid);
	Post getPost(long id);
	void post(Post post);
	
	List<Post> findRecents(int maxResutls, long ignoreUserId);
	List<Post> findHots(int maxResutls, long ignoreUserId);
	
	void delete(long id, long authorId);
	void delete(long id);
}
