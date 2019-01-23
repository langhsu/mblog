package com.mtons.mblog;

import com.mtons.mblog.base.utils.ImageUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.upyun.UpYunUtils;

import java.io.File;
import java.io.IOException;

/**
 * created by langhsu
 * on 2019/1/22
 */
public class QiniuOSSTest {
    private static final String accessKey = "";
    private static final String secretKey = "";
    private static final String domain = "qiniu.mtons.com";
    private static final String bucket = "mtons";

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("F:/data/a_2.jpg");
        byte[] bytes = ImageUtils.screenshot(file, 360, 200);
        final String key = "/static" + UpYunUtils.md5(bytes) + ".jpg";

        Zone z = Zone.autoZone();
        Configuration configuration = new Configuration(z);

        final Auth auth = Auth.create(accessKey, secretKey);
        final String upToken = auth.uploadToken(bucket, key);
        try {
            final UploadManager uploadManager = new UploadManager(configuration);
            final Response response = uploadManager.put(bytes, key, upToken);
            System.out.println(response.bodyString());
            System.out.println(response.isOK());
        } catch (QiniuException e) {
            final Response r = e.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        String filePath = domain.trim()+ key + ".jpg";
        System.out.println(filePath);
    }
}
