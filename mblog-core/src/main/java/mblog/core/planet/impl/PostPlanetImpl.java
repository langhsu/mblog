/**
 * 
 */
package mblog.core.planet.impl;

import mblog.core.planet.PostPlanet;
import mblog.core.pojos.Mblog;
import mblog.core.service.MblogService;
import mtons.commons.pojos.Paging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author langhsu
 * 
 */
public class PostPlanetImpl implements PostPlanet {
	@Autowired
	private MblogService mblogService;

	@Override
	@Cacheable(value = "postCache", key = "'index' + #paging.getPageNo()")
	public Paging paging(Paging paging) {
		mblogService.paging(paging);
		return paging;
	}

	@Override
	@Cacheable(value = "postCache", key = "#id")
	public Mblog getPost(long id) {
		return mblogService.get(id);
	}

	@Override
	@CacheEvict(value = "postCache", key = "#id", allEntries = true)
	public void delete(long id) {
		mblogService.delete(id);
	}

}
