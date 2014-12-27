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
	Posts getPost(long id);
	void delete(long id);
}
