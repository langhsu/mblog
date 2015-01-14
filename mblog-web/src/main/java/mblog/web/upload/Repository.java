/**
 * 
 */
package mblog.web.upload;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author langhsu
 *
 */
public interface Repository {
	String store(MultipartFile file, String basePath) throws IOException;
	String store(File file, String basePath) throws IOException;
	String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception;
	String storeScale(File file, String basePath, int maxWidth) throws Exception;
}
