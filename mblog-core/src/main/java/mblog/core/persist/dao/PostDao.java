/**
 * 
 */
package mblog.core.persist.dao;

import java.util.Collection;
import java.util.List;

import mblog.core.persist.entity.PostPO;
import mtons.modules.persist.Dao;
import mtons.modules.pojos.Page;

import org.hibernate.Session;

/**
 * @author langhsu
 *
 */
public interface PostDao extends Dao<PostPO> {
	Session getSession();
	List<PostPO> paging(Page page);
	List<PostPO> pagingByUserId(Page page, long userId);
	List<PostPO> findRecents(int maxResutls, long ignoreUserId);
	List<PostPO> findHots(int maxResutls, long ignoreUserId);
	List<PostPO> findByIds(Collection<Long> ids);
}
