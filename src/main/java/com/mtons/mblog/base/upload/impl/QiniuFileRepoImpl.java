package com.mtons.mblog.base.upload.impl;

import com.mtons.mblog.base.context.AppContext;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.upload.FileRepo;
import com.mtons.mblog.base.utils.FileKit;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author langhsu
 * @since  3.0
 */
@Slf4j
@Component
public class QiniuFileRepoImpl extends AbstractFileRepo implements FileRepo {
    @Autowired
    private AppContext appContext;

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String accessKey = appContext.getConfig().get("qiniu_oss_key");
        String secretKey = appContext.getConfig().get("qiniu_oss_secret");
        String domain = appContext.getConfig().get("qiniu_oss_domain");
        String bucket = appContext.getConfig().get("qiniu_oss_bucket");
        String src = appContext.getConfig().get("qiniu_oss_src");

        if (StringUtils.isAnyBlank(accessKey, secretKey, domain, bucket)) {
            throw new MtonsException("请先在后台设置阿里云配置信息");
        }

        if (StringUtils.isNotBlank(src)) {
            if (src.startsWith("/")) {
                src = src.substring(1);
            }

            if (!src.endsWith("/")) {
                src = src + "/";
            }
        } else {
            src = "";
        }

        String key = UpYunUtils.md5(bytes);
        String path = src + key + FileKit.getSuffix(pathAndFileName);

        Zone z = Zone.autoZone();
        Configuration configuration = new Configuration(z);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, path);

        UploadManager uploadManager = new UploadManager(configuration);
        Response response = uploadManager.put(bytes, path, upToken);

        if (!response.isOK()) {
            throw new MtonsException(response.bodyString());
        }
        return domain.trim() + "/" + path;
    }

    @Override
    public void deleteFile(String storePath) {
        String accessKey = appContext.getConfig().get("qiniu_oss_key");
        String secretKey = appContext.getConfig().get("qiniu_oss_secret");
        String domain = appContext.getConfig().get("qiniu_oss_domain");
        String bucket = appContext.getConfig().get("qiniu_oss_bucket");

        if (StringUtils.isAnyBlank(accessKey, secretKey, domain, bucket)) {
            throw new MtonsException("请先在后台设置阿里云配置信息");
        }

        String path = StringUtils.remove(storePath, domain.trim());

        Zone z = Zone.autoZone();
        Configuration configuration = new Configuration(z);
        Auth auth = Auth.create(accessKey, secretKey);

        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            bucketManager.delete(bucket, path);
        } catch (QiniuException e) {
            Response r = e.response;
            log.error(e.getMessage(), r.toString());
        }
    }

}
