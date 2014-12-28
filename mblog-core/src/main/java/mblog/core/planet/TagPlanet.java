/**
 * 
 */
package mblog.core.planet;

import java.util.List;

import mblog.core.pojos.Tag;

/**
 * @author langhsu
 *
 */
public interface TagPlanet {
	List<Tag> topTags(int maxResutls, boolean loadPost);
}
