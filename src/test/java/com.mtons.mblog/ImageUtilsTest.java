package com.mtons.mblog;

import com.mtons.mblog.base.utils.FileKit;
import com.mtons.mblog.base.utils.ImageUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * created by langhsu
 * on 2019/1/20
 */
public class ImageUtilsTest {
    public static void main(String[] args) throws Exception {
//        File file = new File("F:/data/test.png");
//        byte[] bytes = ImageUtils.screenshot(Thumbnails.of(file), Positions.CENTER,360, 240);
//        FileKit.writeByteArrayToFile(bytes, "F:/data/test_out.jpg");

        byte[] bytes = ImageUtils.download("https://images.gitee.com/uploads/images/2019/0125/142627_fcd67bfd_116277.jpeg");
        System.out.println(bytes);
    }

}
