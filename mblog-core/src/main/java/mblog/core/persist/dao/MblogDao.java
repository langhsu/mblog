/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.MblogPO;
import mtons.commons.persist.Dao;
import mtons.commons.pojos.Paging;

import org.hibernate.Session;

/**
 * @author langhsu
 *
 */
public interface MblogDao extends Dao<MblogPO> {
	Session getSession();
	List<MblogPO> paging(Paging paging);
	List<MblogPO> pagingByUserId(Paging paging, long userId);
	List<MblogPO> recents(int maxResutls, long ignoreUserId);
}
