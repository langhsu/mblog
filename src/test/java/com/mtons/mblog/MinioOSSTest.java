package com.mtons.mblog;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;

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
    private static final String oss_bucket = "";//blogimage
    private static final String oss_domain = "";//http://47.108.114.116:9000
    private static final String oss_key    = "";//J4KymTNNAsl32BrwUPZy
    private static final String oss_secret = "";//RjJBEhjwjP6MmweOZDQrCL1FkJ1pBgc7nlyOTYFC
    private static final String oss_src    = "minio_oss_src";//?

    public static void main(String[] args) throws IOException, InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        MinioClient minioClient = MinioClient.builder()
                .endpoint(oss_domain)
                .credentials(oss_key,oss_secret)
                .build();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("/Users/hukss/Desktop/截屏2023-11-26 21.02.54.png"));
        minioClient.putObject(PutObjectArgs.builder().bucket(oss_bucket).object("2024/01/04/测试图片.jpg").stream(
                        stream, -1, 10485760)
                .contentType("image/jpeg")
                .build());

    }
}
