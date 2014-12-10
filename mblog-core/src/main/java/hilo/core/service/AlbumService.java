/**
 * 
 */
package hilo.core.service;

import hilo.core.pojos.Album;

import java.util.List;

/**
 * @author langhsu
 *
 */
public interface AlbumService {
	List<Album> list(int projectId, long toId);
	long add(Album album);
}
