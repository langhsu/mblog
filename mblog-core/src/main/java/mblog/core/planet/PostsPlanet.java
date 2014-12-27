/**
 * 
 */
package mblog.core.planet;

import mblog.core.pojos.Posts;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface PostsPlanet {
	Paging paging(Paging paging);
	Paging pagingByUserId(Paging paging, long uid);
	Posts getPost(long id);
	void post(Posts post);
	void delete(long id);
}
