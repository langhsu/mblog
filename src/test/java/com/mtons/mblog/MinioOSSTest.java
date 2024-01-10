package com.mtons.mblog;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * created by langhsu
 * on 2019/1/22
 */
public class MinioOSSTest {
    private static final String oss_bucket = "";
    private static final String oss_domain = "";
    private static final String oss_key    = "";
    private static final String oss_secret = "";
    private static final String oss_src    = "";

    @Test
    public void testStoreMinIO() throws Exception {
        MinioClient minioClient = builder();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("/Users/hukss/Desktop/截屏2023-11-26 21.02.54.png"));
        minioClient.putObject(PutObjectArgs.builder().bucket(oss_bucket).object("2024/01/04/测试图片.jpg").stream(
                        stream, -1, 10485760)
                .contentType("image/jpeg")
                .build());

    }
    @Test
    public void testRemoveMinIOObject() throws Exception {

        MinioClient client = builder();

        client.removeObject(
                RemoveObjectArgs.builder().bucket(oss_bucket).object("/2024/01/04/测试图片.jpg").build());
    }

    private MinioClient builder() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(oss_domain)
                .credentials(oss_key,oss_secret)
                .build();
        return minioClient;
    }
}
