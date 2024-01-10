package com.mtons.mblog.base.storage.impl;

import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.storage.Storage;
import com.mtons.mblog.base.utils.FileKit;
import com.upyun.UpYunUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
@Component
@Slf4j
public class MinioStorageImpl extends AbstractStorage implements Storage {
    private static final String oss_bucket = "minio_oss_bucket";
    private static final String oss_domain = "minio_oss_domain";
    private static final String oss_key    = "minio_oss_key";
    private static final String oss_secret = "minio_oss_secret";
    private static final String oss_src    = "minio_oss_src";

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String accessKey = options.getValue(oss_key);
        String secretKey = options.getValue(oss_secret);
        String domain = options.getValue(oss_domain);
        String bucket = options.getValue(oss_bucket);
        String src = options.getValue(oss_src);
        if (StringUtils.isAnyBlank(accessKey, secretKey, domain, bucket)) {
            throw new MtonsException("请先在后台设置Minio配置信息");
        }
        if (StringUtils.isBlank(src)) {
            src = "";
        } else {
            if (src.startsWith("/")) {
                src = src.substring(1);
            }

            if (!src.endsWith("/")) {
                src = src + "/";
            }
        }

        String key = UpYunUtils.md5(bytes);
        String path = src + key + FileKit.getSuffix(pathAndFileName);

        MinioClient minioClient = builder();
        minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .stream(new ByteArrayInputStream(bytes),-1,10485760)
                        .contentType("image/jpeg")
                .build());
        return domain + "/" + bucket.trim()  + "/" + path;
    }

    @Override
    public void deleteFile(String storePath) {

        MinioClient client = builder();
        String bucket = options.getValue(oss_bucket);
        String path = StringUtils.remove(storePath,options.getValue(oss_domain) + "/" + bucket.trim());
        try {
            client.removeObject(
                    RemoveObjectArgs.builder().bucket(bucket).object(path).build());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
    private MinioClient builder() {
        String accessKey = options.getValue(oss_key);
        String secretKey = options.getValue(oss_secret);
        String domain = options.getValue(oss_domain);

        if (StringUtils.isAnyBlank(domain, accessKey, secretKey)) {
            throw new MtonsException("请先在后台设置MinIO配置信息");
        };
        MinioClient minioClient = MinioClient.builder()
                .endpoint(domain)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}
