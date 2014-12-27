/**
 * 
 */
package mblog.core.planet;

import mblog.core.pojos.Post;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface PostPlanet {
	Paging paging(Paging paging);
	Paging pagingByUserId(Paging paging, long uid);
	Post getPost(long id);
	void post(Post post);
	void delete(long id);
}
