/**
 * 
 */
package hilo.core.service;

import hilo.core.pojos.Project;

import java.util.List;

/**
 * @author langhsu
 *
 */
public interface ProjectService {
	List<Project> list(int level);
}
