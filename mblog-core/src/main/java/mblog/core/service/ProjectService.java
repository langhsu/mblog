/**
 * 
 */
package mblog.core.service;

import java.util.List;

import mblog.core.pojos.Project;

/**
 * @author langhsu
 *
 */
public interface ProjectService {
	List<Project> list(int level);
}
