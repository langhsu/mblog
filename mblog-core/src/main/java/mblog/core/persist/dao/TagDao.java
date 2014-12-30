/**
 * 
 */
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.persist.entity.TagPO;
import mtons.modules.persist.Dao;

/**
 * @author langhsu
 *
 */
public interface TagDao extends Dao<TagPO> {
	TagPO getByName(String name);
	List<TagPO> tops(int maxResutls);
}
