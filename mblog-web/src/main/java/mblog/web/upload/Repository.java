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
	/**
	 * 保存临时图片
	 * - 临时图片不会生成复杂的多级目录
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String temp(MultipartFile file, String basePath) throws IOException;
	
	/**
	 * 保存压缩后的临时图片
	 * - 临时图片不会生成复杂的多级目录
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String tempScale(MultipartFile file, String basePath, int maxWidth) throws Exception;
	
	/**
	 * 存储图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String store(MultipartFile file, String basePath) throws IOException;
	
	/**
	 * 存储图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String store(File file, String basePath) throws IOException;
	
	/**
	 * 存储压缩图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception;
	
	/**
	 * 存储压缩图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String storeScale(File file, String basePath, int maxWidth) throws Exception;
}
