/**
 * 
 */
package mblog.core.service;

import java.util.List;

import mblog.core.pojos.Mblog;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface MblogService {
	void paging(Paging paging);
	void pagingByUserId(Paging paging, long userId);
	
	List<Mblog> recents(int maxResutls, long ignoreUserId);
	
	void add(Mblog mblog);
	Mblog get(long id);
	void delete(long id);
}
