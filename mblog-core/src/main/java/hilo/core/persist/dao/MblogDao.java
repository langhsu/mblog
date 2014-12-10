/**
 * 
 */
package hilo.core.persist.dao;

import hilo.core.persist.entity.MblogPO;

import java.util.List;

import mtons.commons.persist.Dao;
import mtons.commons.pojos.Paging;

/**
 * @author langhsu
 *
 */
public interface MblogDao extends Dao<MblogPO> {
	List<MblogPO> paging(Paging paging, int projectId);
	List<MblogPO> pagingByUserId(Paging paging, long userId);
	List<MblogPO> recents(int maxResutls, long ignoreUserId);
}
