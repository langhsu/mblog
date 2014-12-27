/**
 * 
 */
package mblog.core.planet.impl;

import mblog.core.planet.PostsPlanet;
import mblog.core.pojos.Posts;
import mblog.core.service.PostsService;
import mtons.commons.pojos.Paging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author langhsu
 * 
 */
public class PostsPlanetImpl implements PostsPlanet {
	@Autowired
	private PostsService mblogService;

	@Override
	@Cacheable(value = "postsCaches", key = "'index-' + #paging.getPageNo()")
	public Paging paging(Paging paging) {
		mblogService.paging(paging);
		return paging;
	}

	@Override
	@Cacheable(value = "postsCaches", key = "#id")
	public Posts getPost(long id) {
		return mblogService.get(id);
	}

	@Override
	@CacheEvict(value = "postsCaches", key = "#id", allEntries = true)
	public void delete(long id) {
		mblogService.delete(id);
	}

}
