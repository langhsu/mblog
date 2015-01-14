/**
 * 
 */
package mblog.web.upload.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import mblog.core.context.AppContext;
import mblog.core.utils.FileNameUtils;
import mblog.web.upload.Repository;
import mtons.modules.utils.GMagickUtils;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author langhsu
 *
 */
public class FileRepository extends AbstractFileRepository implements Repository, ServletContextAware {
	@Autowired
	private AppContext appContext;
	
	private ServletContext context;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}
	
	@Override
	public String store(MultipartFile file, String basePath) throws IOException {
		validateFile(file);
		
		String realPath = context.getRealPath("/");
		
		String path = FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));
		
		File temp = new File(realPath + basePath + path);
		if (!temp.getParentFile().exists()) {
			temp.getParentFile().mkdirs();
		}
		file.transferTo(temp);
		return basePath + path;
	}
	
	@Override
	public String store(File file, String basePath) throws IOException {
		String realPath = context.getRealPath("/");
		
		String path = FileNameUtils.genPathAndFileName(getExt(file.getName()));
		
		File dist = new File(realPath + basePath + path);
		if (!dist.getParentFile().exists()) {
			dist.getParentFile().mkdirs();
		}
		FileUtils.copyDirectory(file, dist);
		return basePath + path;
	}

	@Override
	public String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception {
		validateFile(file);
		
		String realPath = context.getRealPath("/");
		
		String path = FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));
		
		File temp = new File(realPath + appContext.getTempDir() + path);
		if (!temp.getParentFile().exists()) {
			temp.getParentFile().mkdirs();
		}
		file.transferTo(temp);
		
		String dest = realPath + basePath + path;
		
		GMagickUtils.scaleImageByWidth(temp.getAbsolutePath(), dest, maxWidth);
		
		temp.delete();
		return basePath + path;
	}

	@Override
	public String storeScale(File file, String basePath, int maxWidth) throws Exception {
		String realPath = context.getRealPath("/");
		
		String path = FileNameUtils.genPathAndFileName(getExt(file.getName()));
		
		File dist = new File(realPath + basePath + path);
		if (!dist.getParentFile().exists()) {
			dist.getParentFile().mkdirs();
		}
		GMagickUtils.scaleImageByWidth(file.getAbsolutePath(), dist.getAbsolutePath(), maxWidth);
		return basePath + path;
	}

}
