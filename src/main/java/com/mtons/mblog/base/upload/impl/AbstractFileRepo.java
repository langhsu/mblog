/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.upload.impl;

import com.mtons.mblog.base.context.AppContext;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.upload.FileRepo;
import com.mtons.mblog.base.utils.FileNameUtils;
import com.mtons.mblog.base.utils.ImageUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author langhsu
 */
public abstract class AbstractFileRepo implements FileRepo {
    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    protected AppContext appContext;

    // 文件允许格式
    protected String[] allowFiles = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};

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
    public String store(MultipartFile file, String basePath) throws IOException {
        validateFile(file);

        String path = basePath + FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));
        File temp = new File(getRoot() + path);
        checkDirAndCreate(temp);
        file.transferTo(temp);
        return path;
    }

    @Override
    public String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception {
        validateFile(file);

        String path = basePath + FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));
        // 根据临时文件生成略缩图
        String dest = getRoot() + path;
        ImageUtils.scaleImageByWidth(file, dest, maxWidth);
        return path;
    }

    @Override
    public String storeScale(MultipartFile file, String basePath, int width, int height) throws Exception {
        validateFile(file);

        String path = basePath + FileNameUtils.genPathAndFileName(getExt(file.getOriginalFilename()));

        // 根据临时文件生成略缩图
        String dest = getRoot() + path;
        ImageUtils.scale(file, dest, width, height);
        return path;
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

            ret = new int[]{w, h};

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public void deleteFile(String storePath) {
        File file = new File(getRoot() + storePath);

        // 文件存在, 且不是目录
        if (file.exists() && !file.isDirectory()) {
            file.delete();
            log.info("fileRepo delete " + storePath);
        }
    }

}
