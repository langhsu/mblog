/**
 * 
 */
package mblog.core.planet.impl;

import mblog.core.planet.PostPlanet;
import mblog.core.pojos.Post;
import mblog.core.service.PostService;
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
	private PostService postsService;

	@Override
	@Cacheable(value = "postsCaches", key = "'list_' + #paging.getPageNo() + '_' + #paging.getMaxResults()")
	public Paging paging(Paging paging) {
		postsService.paging(paging);
		return paging;
	}
	
	@Override
	@Cacheable(value = "postsCaches", key = "'uhome' + #uid + '_' + #paging.getPageNo()")
	public Paging pagingByUserId(Paging paging, long uid) {
		postsService.pagingByUserId(paging, uid);
		return paging;
	}

	@Override
	@Cacheable(value = "postsCaches", key = "'view_' + #id")
	public Post getPost(long id) {
		return postsService.get(id);
	}

	@Override
	@CacheEvict(value = "postsCaches", allEntries = true)
	public void post(Post post) {
		postsService.post(post);
	}
	
	@Override
	@CacheEvict(value = "postsCaches", key = "#id", allEntries = true)
	public void delete(long id) {
		postsService.delete(id);
	}

}
