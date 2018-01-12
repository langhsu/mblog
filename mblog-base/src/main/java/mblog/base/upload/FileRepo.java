/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author langhsu
 *
 */
public interface FileRepo {
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

	/**
	 * 存储压缩图片
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String storeScale(File file, String basePath, int width, int height) throws Exception;
	
	/**
	 * 获取图片大小
	 *
	 * @param storePath
	 * @return
	 */
	int[] imageSize(String storePath);

	String getRoot();

	/**
	 * 存储路径
	 * @param storePath
	 */
	void deleteFile(String storePath);

}
