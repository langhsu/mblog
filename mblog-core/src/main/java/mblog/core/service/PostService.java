/**
 * 
 */
package mblog.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mblog.core.pojos.Post;
import mtons.commons.pojos.Paging;

import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

/**
 * @author langhsu
 *
 */
public interface PostService {
	void paging(Paging paging);
	void pagingByUserId(Paging paging, long userId);
	List<Post> search(Paging paging, String q) throws InterruptedException, IOException, InvalidTokenOffsetsException;
	List<Post> searchByTag(Paging paging, String tag) throws InterruptedException, IOException, InvalidTokenOffsetsException;
	List<Post> recents(int maxResutls, long ignoreUserId);
	Map<Long, Post> findByIds(Set<Long> ids);
	
	void post(Post post);
	Post get(long id);
	void delete(long id);
	
	void updateView(long id);
	void updateHeart(long id);
	
}
