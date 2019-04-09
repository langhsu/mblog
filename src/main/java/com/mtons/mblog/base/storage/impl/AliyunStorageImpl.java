/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.storage.impl;

import com.aliyun.oss.OSSClient;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.storage.Storage;
import com.mtons.mblog.base.utils.FileKit;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * @author langhsu
 * @since  3.0
 */
@Slf4j
@Component
public class AliyunStorageImpl extends AbstractStorage implements Storage {
    private static final String oss_endpoint = "aliyun_oss_endpoint";
    private static final String oss_bucket   = "aliyun_oss_bucket";
    private static final String oss_key      = "aliyun_oss_key";
    private static final String oss_secret   = "aliyun_oss_secret";
    private static final String oss_src      = "aliyun_oss_src";

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String endpoint = options.getValue(oss_endpoint);
        String bucket = options.getValue(oss_bucket);
        String src = options.getValue(oss_src);

        if (StringUtils.isAnyBlank(endpoint, bucket)) {
            throw new MtonsException("请先在后台设置阿里云配置信息");
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
        OSSClient client = builder();
        client.putObject(bucket, path, new ByteArrayInputStream(bytes));
        return "//" + bucket.trim() + "." + endpoint.trim() + "/" + path;
    }

    @Override
    public void deleteFile(String storePath) {
        String bucket = options.getValue(oss_bucket);
        String endpoint = options.getValue(oss_endpoint);
        String path = StringUtils.remove(storePath, "//" + bucket.trim() + "." + endpoint.trim() + "/");
        OSSClient client = builder();
        try {
            client.doesObjectExist(bucket, path);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private OSSClient builder() {
        String endpoint = options.getValue(oss_endpoint);
        String accessKeyId = options.getValue(oss_key);
        String accessKeySecret = options.getValue(oss_secret);

        if (StringUtils.isAnyBlank(endpoint, accessKeyId, accessKeySecret)) {
            throw new MtonsException("请先在后台设置阿里云配置信息");
        }
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
}
