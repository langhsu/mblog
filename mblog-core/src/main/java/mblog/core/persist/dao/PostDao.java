/**
 * 
 */
package mblog.core.persist.dao;

import java.util.Collection;
import java.util.List;

import mblog.core.persist.entity.PostPO;
import mtons.commons.persist.Dao;
import mtons.commons.pojos.Paging;

import org.hibernate.Session;

/**
 * @author langhsu
 *
 */
public interface PostDao extends Dao<PostPO> {
	Session getSession();
	List<PostPO> paging(Paging paging);
	List<PostPO> pagingByUserId(Paging paging, long userId);
	List<PostPO> recents(int maxResutls, long ignoreUserId);
	List<PostPO> findByIds(Collection<Long> ids);
}
