/**
 * 
 */
package mblog.core.planet;

import mblog.core.pojos.Mblog;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface PostPlanet {
	Paging paging(Paging paging);
	Mblog getPost(long id);
	void delete(long id);
}
