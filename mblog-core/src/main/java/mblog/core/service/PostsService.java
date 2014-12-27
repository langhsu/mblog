/**
 * 
 */
package mblog.core.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import mblog.core.pojos.Posts;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface PostsService {
	void paging(Paging paging);
	void pagingByUserId(Paging paging, long userId);
	List<Posts> search(Paging paging, String q) throws InterruptedException, IOException, InvalidTokenOffsetsException;
	List<Posts> recents(int maxResutls, long ignoreUserId);
	
	void post(Posts mblog);
	Posts get(long id);
	void delete(long id);
	
	void updateView(long id);
	void updateHeart(long id);
	
}
