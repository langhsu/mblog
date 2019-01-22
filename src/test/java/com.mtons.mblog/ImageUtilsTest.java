package com.mtons.mblog;

import com.mtons.mblog.base.utils.FileKit;
import com.mtons.mblog.base.utils.ImageUtils;

import java.io.File;
import java.io.IOException;

/**
 * created by langhsu
 * on 2019/1/20
 */
public class ImageUtilsTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("F:/data/a_2.jpg");
        byte[] bytes = ImageUtils.screenshot(file, 360, 200);
        FileKit.writeByteArrayToFile(bytes, "F:/data/test.jpg");
    }
}
