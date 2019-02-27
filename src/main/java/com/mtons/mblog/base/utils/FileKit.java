package com.mtons.mblog.base.utils;

import org.apache.commons.io.FileUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author - langhsu
 * @create - 2018/3/9
 */
public class FileKit {
    // 文件允许格式
    private static List<String> allowFiles = Arrays.asList(".gif", ".png", ".jpg", ".jpeg", ".bmp");
    private final static String PREFIX_VIDEO="video/";
    private final static String PREFIX_IMAGE="image/";

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    public static boolean checkFileType(String fileName) {
        Iterator<String> type = allowFiles.iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public static String getFilename(@NotNull String filename) {
        int pos = filename.lastIndexOf(".");
        return filename.substring(0, pos);
    }

    public static String getSuffix(String filename) {
        int pos = filename.lastIndexOf(".");
        return filename.substring(pos);
    }

    public static void writeByteArrayToFile(byte[] bytes, String dest) throws IOException {
        FileUtils.writeByteArrayToFile(new File(dest), bytes);
    }


}
