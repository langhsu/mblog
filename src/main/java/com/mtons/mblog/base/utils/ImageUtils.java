package com.mtons.mblog.base.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author langhsu on 2015/9/4.
 */
public class ImageUtils {
    private static Logger log = Logger.getLogger(ImageUtils.class);

    public static void validate(String dest) throws IOException {
        File destFile = new File(dest);
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (destFile.getParentFile() != null && !destFile.getParentFile().exists() && !destFile.getParentFile().mkdirs()) {
            throw new IOException("Destination \'" + dest + "\' directory cannot be created");
        } else if (destFile.exists() && !destFile.canWrite()) {
            throw new IOException("Destination \'" + dest + "\' exists but is read-only");
        }
    }

    /**
     * 下载远程图片到本地，用于第三方登录下载头像
     *
     * @param urlString 图片链接
     * @param savePath  保存路径
     * @throws Exception
     * @author A蛋壳  2015年9月13日 上午9:40:17
     */
    public static void download(String urlString, String savePath) throws Exception {

        URL url = new URL(urlString);    // 构造URL
        URLConnection connection = url.openConnection();    // 打开连接
        connection.setConnectTimeout(5 * 1000);        // 设置请求超时时间
        InputStream is = connection.getInputStream();    // 输入流

        byte[] bs = new byte[1024];        // 1K的数据缓存
        int len;
        File sf = new File(savePath);
        if (sf.getParentFile() != null && sf.getParentFile().exists() == false) {
            if (sf.getParentFile().mkdirs() == false) {
                throw new IOException("Destination '" + savePath + "' directory cannot be created");
            }
        }

        OutputStream os = new FileOutputStream(savePath);
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        os.close();
        is.close();
    }

    /**
     * 根据最大宽度图片压缩
     *
     * @param file    原图位置
     * @param dest    目标位置
     * @param maxSize 指定压缩后最大边长
     * @return boolean
     * @throws IOException
     */
    public static boolean scaleImageByWidth(MultipartFile file, String dest, int maxSize) throws IOException {
        BufferedImage src = ImageIO.read(file.getInputStream()); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();

        log.debug("origin with/height " + w + "/" + h);

        int size = (int) Math.max(w, h);
        int tow = w;
        int toh = h;

        if (size > maxSize) {
            if (w > maxSize) {
                tow = maxSize;
                toh = h * maxSize / w;
            } else {
                tow = w * maxSize / h;
                toh = maxSize;
            }
        }
        scale(file, dest, tow, toh);
        return true;
    }

    public static void scale(MultipartFile file, String dest, int width, int height) throws IOException {
        validate(dest);
        log.debug("scaled with/height : " + width + "/" + height);
        Thumbnails.of(file.getInputStream()).size(width, height).toFile(dest);
    }

    public static void scale(File file, String dest, int width, int height) throws IOException {
        validate(dest);
        log.debug("scaled with/height : " + width + "/" + height);
        Thumbnails.of(file).size(width, height).toFile(dest);
    }

    /**
     * 图片压缩,各个边按比例压缩
     *
     * @param file    原图位置
     * @param dest    目标位置
     * @param maxSize 指定压缩后最大边长
     * @return boolean
     * @throws IOException
     */
    public static boolean scaleImage(MultipartFile file, String dest, int maxSize) throws IOException {
        BufferedImage src = ImageIO.read(file.getInputStream()); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();

        log.debug("origin with/height " + w + "/" + h);

        int size = Math.max(w, h);
        int tow = w;
        int toh = h;

        if (size > maxSize) {
            if (w > maxSize) {
                tow = maxSize;
                toh = h * maxSize / w;
            } else {
                tow = w * maxSize / h;
                toh = maxSize;
            }
        }

        log.debug("scaled with/height : " + tow + "/" + toh);

        scale(file, dest, tow, toh);
        return true;
    }

    public static boolean scaleImage(File file, String dest, int maxSize) throws IOException {
        BufferedImage src = ImageIO.read(file); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();

        log.debug("origin with/height " + w + "/" + h);

        int size = Math.max(w, h);
        int tow = w;
        int toh = h;

        if (size > maxSize) {
            if (w > maxSize) {
                tow = maxSize;
                toh = h * maxSize / w;
            } else {
                tow = w * maxSize / h;
                toh = maxSize;
            }
        }

        log.debug("scaled with/height : " + tow + "/" + toh);

        scale(file, dest, tow, toh);
        return true;
    }

    /**
     * 裁剪图片
     *
     * @param file   源图片路径
     * @param dest   处理后图片路径
     * @param x      起始X坐标
     * @param y      起始Y坐标
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return boolean
     * @throws IOException          io异常
     * @throws InterruptedException 中断异常
     */
    public static boolean cutImage(File file, String dest, int x, int y, int width, int height) throws IOException, InterruptedException {
        validate(dest);
        Thumbnails.of(file).sourceRegion(x, y, width, height).size(width, height).keepAspectRatio(false).toFile(dest);
        return true;
    }

    public static boolean cutImage(File file, String dest, int x, int y, int size) throws IOException, InterruptedException {
        return cutImage(file, dest, x, y, size, size);
    }

}
