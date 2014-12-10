/**
 * 
 */
package mblog.core.service;

import java.util.List;

import mblog.core.pojos.Album;

/**
 * @author langhsu
 *
 */
public interface AlbumService {
	List<Album> list(long toId);
	long add(Album album);
}
