/**
 * 
 */
package mblog.core.service;

import java.util.List;

import mblog.core.pojos.Setting;

/**
 * @author langhsu
 *
 */
public interface SettingService {
	List<Setting> findAll();
	void update(List<Setting> settings);
}
