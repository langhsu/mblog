package com.mtons.mblog.base.storage.impl;

import com.UpYun;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.storage.Storage;
import com.mtons.mblog.base.utils.FileKit;
import com.mtons.mblog.config.SiteOptions;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * created by langhsu
 * on 2019/1/20
 *
 * @since 3.0
 */
@Slf4j
@Component
public class UpYunStorageImpl extends AbstractStorage implements Storage {
    @Autowired
    private SiteOptions siteOptions;

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String domain = siteOptions.getOptions().get("upyun_oss_domain");
        String src = siteOptions.getOptions().get("upyun_oss_src");

        if (StringUtils.isAnyBlank(domain)) {
            throw new MtonsException("请先在后台设置又拍云配置信息");
        }

        if (StringUtils.isBlank(src)) {
            src = "";
        } else {
            if (!src.startsWith("/")) {
                src = "/" + src;
            }

            if (!src.endsWith("/")) {
                src = src + "/";
            }
        }

        String key = UpYunUtils.md5(bytes);
        String path = src + key + FileKit.getSuffix(pathAndFileName);
        UpYun upYun = builder();
        upYun.setContentMD5(key);
        upYun.writeFile(path, bytes, true);
        return domain.trim() + path;
    }

    @Override
    public void deleteFile(String storePath) {
        String domain = siteOptions.getOptions().get("upyun_oss_domain");
        String path = StringUtils.remove(storePath, domain.trim());
        UpYun yun = builder();
        try {
            yun.deleteFile(path);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private UpYun builder() {
        String bucket = siteOptions.getOptions().get("upyun_oss_bucket");
        String operator = siteOptions.getOptions().get("upyun_oss_operator");
        String password = siteOptions.getOptions().get("upyun_oss_password");

        if (StringUtils.isAnyBlank(bucket, operator, password)) {
            throw new MtonsException("请先在后台设置又拍云配置信息");
        }
        UpYun yun = new UpYun(bucket, operator, password);
        yun.setTimeout(60);
        yun.setApiDomain(UpYun.ED_AUTO);
        yun.setDebug(true);
        return yun;
    }
}
