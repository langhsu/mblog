package com.mtons.mblog.base.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Coordinate;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author langhsu on 2019/1/20.
 * @since 3.0
 */
@Slf4j
public class ImageUtils {

    public static void validate(String dest) throws IOException {
        File destFile = new File(dest);
        if (destFile.getParentFile() != null && !destFile.getParentFile().exists() && !destFile.getParentFile().mkdirs()) {
            throw new IOException("Destination \'" + dest + "\' directory cannot be created");
        } else if (destFile.exists() && !destFile.canWrite()) {
            throw new IOException("Destination \'" + dest + "\' exists but is read-only");
        }
    }

    public static byte[] download(String urlString) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Thumbnails.of(new URL(urlString)).scale(1f).outputQuality(0.75f).toOutputStream(output);
        return output.toByteArray();
    }

    /**
     * 图片压缩,各个边按比例压缩
     *
     * @param builder Thumbnails.of
     * @param width   压缩后的宽度
     * @param height  压缩后的高度
     * @param <T>     T
     * @throws IOException IOException
     */
    public static <T> byte[] scale(Thumbnails.Builder<T> builder, int width, int height) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        builder.size(width, height).toOutputStream(output);
        return output.toByteArray();
    }

    /**
     * 根据最大宽度图片压缩
     *
     * @param file    原图位置
     * @param maxSize 指定压缩后最大边长
     * @throws IOException IOException
     */
    public static byte[] scaleByWidth(MultipartFile file, int maxSize) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream()).width(maxSize).toOutputStream(output);
        return output.toByteArray();
    }

    public static byte[] scale(File file, int width, int height) throws IOException {
        return scale(Thumbnails.of(file), width, height);
    }

    public static byte[] scale(File file, int maxSize) throws IOException {
        return scale(file, maxSize, maxSize);
    }

    public static byte[] scale(MultipartFile file, int width, int height) throws IOException {
        return scale(Thumbnails.of(file.getInputStream()), width, height);
    }

    public static byte[] scale(MultipartFile file, int maxSize) throws IOException {
        return scale(file, maxSize, maxSize);
    }

    /**
     * 截图
     *
     * @param builder  Thumbnails.of
     * @param position the position
     * @param width    width
     * @param height   height
     * @param <T>      T
     * @throws IOException          IOException
     * @throws InterruptedException InterruptedException
     */
    public static <T> byte[] screenshot(Thumbnails.Builder<T> builder, Position position, int width, int height) throws IOException, InterruptedException {
        BufferedImage image = builder.size(width, height).asBufferedImage();
        image = Thumbnails.of(image).size(width, height).crop(position).asBufferedImage();
        return toByte(image);
    }

    public static byte[] screenshot(File file, int x, int y, int width, int height) throws IOException, InterruptedException {
        return screenshot(Thumbnails.of(file), new Coordinate(x, y), width, height);
    }

    public static byte[] screenshot(File file, int x, int y, int size) throws IOException, InterruptedException {
        return screenshot(file, x, y, size, size);
    }

    public static byte[] screenshot(File file, int width, int height) throws IOException, InterruptedException {
        return screenshot(Thumbnails.of(file), Positions.CENTER, width, height);
    }

    public static byte[] screenshot(MultipartFile file, int x, int y, int width, int height) throws IOException, InterruptedException {
        return screenshot(Thumbnails.of(file.getInputStream()), new Coordinate(x, y), width, height);
    }

    public static byte[] screenshot(MultipartFile file, int x, int y, int size) throws IOException, InterruptedException {
        return screenshot(file, x, y, size, size);
    }

    public static byte[] screenshot(MultipartFile file, int width, int height) throws IOException, InterruptedException {
        return screenshot(Thumbnails.of(file.getInputStream()), Positions.CENTER, width, height);
    }

    private static byte[] toByte(BufferedImage image) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "png", output);
        output.flush();
        byte[] bytes = output.toByteArray();
        output.close();
        return bytes;
    }
}
