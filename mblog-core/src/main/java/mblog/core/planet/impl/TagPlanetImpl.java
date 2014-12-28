/**
 * 
 */
package mblog.core.planet.impl;

import java.util.List;

import mblog.core.planet.TagPlanet;
import mblog.core.pojos.Tag;
import mblog.core.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author langhsu
 * 
 */
public class TagPlanetImpl implements TagPlanet {
	@Autowired
	private TagService tagService;

	@Override
	@Cacheable(value = "tagsCaches", key = "'top_tags_' + #maxResutls + '_' + #loadPost")
	public List<Tag> topTags(int maxResutls, boolean loadPost) {
		return tagService.topTags(maxResutls, loadPost);
	}
}
