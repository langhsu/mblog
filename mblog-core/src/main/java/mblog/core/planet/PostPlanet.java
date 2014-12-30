/**
 * 
 */
package mblog.core.planet;

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
	void delete(long id);
}
