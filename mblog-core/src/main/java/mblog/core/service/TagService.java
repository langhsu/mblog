/**
 * 
 */
package mblog.core.service;

import java.util.List;

import mblog.core.pojos.Tag;

/**
 * @author langhsu
 *
 */
public interface TagService {
	List<Tag> topTags(int maxResutls, boolean loadPost);
	
	void batchPost(List<Tag> tags);
	void updateHot(String name);
	void updateHot(long id);
}
