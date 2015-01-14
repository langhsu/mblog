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
	/**
	 * top 查询 Tag
	 * @param maxResutls
	 * @param loadPost 是否加载 tag 最后更新文章
	 * @return
	 */
	List<Tag> topTags(int maxResutls, boolean loadPost);
	
	/**
	 * 评论添加 Tag
	 * @param tags
	 */
	void batchPost(List<Tag> tags);
	
	/**
	 * 更新热度
	 * @param name
	 */
	void identityHots(String name);
	
	/**
	 * 更新热度
	 * @param name
	 */
	void identityHots(long id);
}
