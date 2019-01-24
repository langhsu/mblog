package com.mtons.mblog.base.storage.impl;

import com.aliyun.oss.OSSClient;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.storage.Storage;
import com.mtons.mblog.base.utils.FileKit;
import com.mtons.mblog.config.SiteOptions;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * @author langhsu
 * @since  3.0
 */
@Slf4j
@Component
public class AliyunStorageImpl extends AbstractStorage implements Storage {
    @Autowired
    private SiteOptions siteOptions;

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String endpoint = siteOptions.getOptions().get("aliyun_oss_endpoint");
        String bucket = siteOptions.getOptions().get("aliyun_oss_bucket");
        String src = siteOptions.getOptions().get("aliyun_oss_src");

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
        String bucket = siteOptions.getOptions().get("aliyun_oss_bucket");
        String endpoint = siteOptions.getOptions().get("aliyun_oss_endpoint");
        String path = StringUtils.remove(storePath, "//" + bucket.trim() + "." + endpoint.trim() + "/");
        OSSClient client = builder();
        try {
            client.doesObjectExist(bucket, path);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private OSSClient builder() {
        String endpoint = siteOptions.getOptions().get("aliyun_oss_endpoint");
        String accessKeyId = siteOptions.getOptions().get("aliyun_oss_key");
        String accessKeySecret = siteOptions.getOptions().get("aliyun_oss_secret");

        if (StringUtils.isAnyBlank(endpoint, accessKeyId, accessKeySecret)) {
            throw new MtonsException("请先在后台设置阿里云配置信息");
        }
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
}
