/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.PostsPO;
import mtons.commons.persist.Dao;
import mtons.commons.pojos.Paging;

import org.hibernate.Session;

/**
 * @author langhsu
 *
 */
public interface PostsDao extends Dao<PostsPO> {
	Session getSession();
	List<PostsPO> paging(Paging paging);
	List<PostsPO> pagingByUserId(Paging paging, long userId);
	List<PostsPO> recents(int maxResutls, long ignoreUserId);
}
