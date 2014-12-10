/**
 * 
 */
package hilo.core.service;

import hilo.core.pojos.Mblog;

import java.util.List;

import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface MblogService {
	void paging(Paging paging, int projectId);
	void pagingByUserId(Paging paging, long userId);
	
	List<Mblog> recents(int maxResutls, long ignoreUserId);
	
	void add(Mblog mblog);
	Mblog get(long id);
}
