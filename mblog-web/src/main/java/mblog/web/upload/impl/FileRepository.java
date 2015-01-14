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
	public String temp(MultipartFile file, String basePath) throws IOException {
		validateFile(file);
		
		String root = context.getRealPath("/");
		
		String name = FileNameUtils.genFileName(getExt(file.getOriginalFilename()));
		String path = basePath + "/" + name;
		File temp = new File(root + path);
		checkDirAndCreate(temp);
		file.transferTo(temp);
		return path;
	}
	
	@Override
	public String tempScale(MultipartFile file, String basePath, int maxWidth) throws Exception {
		validateFile(file);
		
		String root = context.getRealPath("/");
		
		String name = FileNameUtils.genFileName(getExt(file.getOriginalFilename()));
		String path = basePath + "/" + name;
		
		// 存储临时文件
		File temp = new File(root + path);
		checkDirAndCreate(temp);
		
		try {
			file.transferTo(temp);
			
			// 根据临时文件生成略缩图
			String scaleName = FileNameUtils.genFileName(getExt(file.getOriginalFilename()));
			String dest = root + basePath + "/" + scaleName;
			
			GMagickUtils.scaleImageByWidth(temp.getAbsolutePath(), dest, maxWidth);
			path = basePath + "/" + scaleName;
		} catch (Exception e) {
			throw e;
		} finally {
			if (temp != null) {
				temp.delete();
			}
		}
		return path;
	}
	
	@Override
	public String store(MultipartFile file, String basePath) throws IOException {
		validateFile(file);
		
		String root = context.getRealPath("/");
		
		String path = FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));
		
		File temp = new File(root + basePath + path);
		checkDirAndCreate(temp);
		file.transferTo(temp);
		return basePath + path;
	}
	
	@Override
	public String store(File file, String basePath) throws IOException {
		String root = context.getRealPath("/");
		
		String path = FileNameUtils.genPathAndFileName(getExt(file.getName()));
		
		File dest = new File(root + basePath + path);
		checkDirAndCreate(dest);
		FileUtils.copyDirectory(file, dest);
		return basePath + path;
	}

	@Override
	public String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception {
		validateFile(file);
		
		String root = context.getRealPath("/");
		
		String path = FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));
		
		File temp = new File(root + appContext.getTempDir() + path);
		checkDirAndCreate(temp);
		
		try {
			file.transferTo(temp);
			
			// 根据临时文件生成略缩图
			String dest = root + basePath + path;
			GMagickUtils.scaleImageByWidth(temp.getAbsolutePath(), dest, maxWidth);
		} catch (Exception e) {
			throw e;
		} finally {
			temp.delete();
		}
		return basePath + path;
	}

	@Override
	public String storeScale(File file, String basePath, int maxWidth) throws Exception {
		String root = context.getRealPath("/");
		String path = FileNameUtils.genPathAndFileName(getExt(file.getName()));
		
		String dest = root + basePath + path;
		GMagickUtils.scaleImageByWidth(file.getAbsolutePath(), dest, maxWidth);
		return basePath + path;
	}

}
