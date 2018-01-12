/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.upload.impl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;

import mblog.base.context.AppContext;
import mblog.base.lang.MtonsException;
import mblog.base.utils.FileNameUtils;
import mblog.base.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import mblog.base.upload.FileRepo;
import mblog.base.upload.FileRepoFactory;

/**
 * @author langhsu
 *
 */
public abstract class AbstractFileRepo implements FileRepo {
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	protected AppContext appContext;
	@Autowired
	protected FileRepoFactory fileRepoFactory;
	
	// 文件允许格式
	protected String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	
	protected void validateFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new MtonsException("文件不能为空");
		}
		
		if (!checkFileType(file.getOriginalFilename())) {
			throw new MtonsException("文件格式不支持");
    	}
	}
	
	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	protected boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
	
	protected String getExt(String filename) {
		int pos = filename.lastIndexOf(".");
		return filename.substring(pos + 1);
	}
	
	protected void checkDirAndCreate(File file) {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
	}

	@Override
	public String temp(MultipartFile file, String basePath) throws IOException {
		validateFile(file);

		String root = getRoot();

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

		String root = getRoot();

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

			ImageUtils.scaleImageByWidth(temp.getAbsolutePath(), dest, maxWidth);

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

		String root = getRoot();

		String path = FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));

		File temp = new File(root + basePath + path);
		checkDirAndCreate(temp);
		file.transferTo(temp);
		return basePath + path;
	}

	@Override
	public String store(File file, String basePath) throws IOException {
		String root = getRoot();

		String path = FileNameUtils.genPathAndFileName(getExt(file.getName()));

		File dest = new File(root + basePath + path);
		checkDirAndCreate(dest);
		FileUtils.copyDirectory(file, dest);
		return basePath + path;
	}

	@Override
	public String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception {
		validateFile(file);

		String root = getRoot();

		String path = FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));

		File temp = new File(root + appContext.getTempDir() + path);
		checkDirAndCreate(temp);

		try {
			file.transferTo(temp);

			// 根据临时文件生成略缩图
			String dest = root + basePath + path;
			ImageUtils.scaleImageByWidth(temp.getAbsolutePath(), dest, maxWidth);

		} catch (Exception e) {
			throw e;
		} finally {
			temp.delete();
		}
		return basePath + path;
	}

	@Override
	public String storeScale(File file, String basePath, int maxWidth) throws Exception {
		String root = getRoot();
		String path = FileNameUtils.genPathAndFileName(getExt(file.getName()));

		String dest = root + basePath + path;
		ImageUtils.scaleImageByWidth(file.getAbsolutePath(), dest, maxWidth);

		return basePath + path;
	}

	@Override
	public String storeScale(File file, String basePath, int width, int height) throws Exception {
		String root = getRoot();
		String path = FileNameUtils.genPathAndFileName(getExt(file.getName()));

		String dest = root + basePath + path;
		ImageUtils.scale(file.getAbsolutePath(), dest, width, height);
		return basePath + path;
	}

	@Override
	public int[] imageSize(String storePath) {
		String root = getRoot();

		File dest = new File(root + storePath);
		int[] ret = new int[2];

		try {
			// 读入文件
			BufferedImage src = ImageIO.read(dest);
			int w = src.getWidth();
			int h = src.getHeight();

			ret = new int[] {w, h};

		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public void deleteFile(String storePath) {
		String root = getRoot();

		File file = new File(root + storePath);

		// 文件存在, 且不是目录
		if (file.exists() && !file.isDirectory()) {
			file.delete();
			log.info("fileRepo delete " + storePath);
		}
	}
	
}
