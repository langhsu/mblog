/**
 * 
 */
package mblog.core.service;

import java.util.List;

import mblog.core.pojos.Attach;

/**
 * @author langhsu
 *
 */
public interface AttachService {
	List<Attach> list(long toId);
	long add(Attach album);
	void deleteByToId(long toId);
}
