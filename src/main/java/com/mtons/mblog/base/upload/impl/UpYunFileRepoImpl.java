package com.mtons.mblog.base.upload.impl;

import com.UpYun;
import com.mtons.mblog.base.context.AppContext;
import com.mtons.mblog.base.lang.MtonsException;
import com.mtons.mblog.base.upload.FileRepo;
import com.mtons.mblog.base.utils.FileKit;
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
public class UpYunFileRepoImpl extends AbstractFileRepo implements FileRepo {
    @Autowired
    private AppContext appContext;

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String domain = appContext.getConfig().get("upyun_oss_domain");
        String src = appContext.getConfig().get("upyun_oss_src");

        if (StringUtils.isAnyBlank(domain, src)) {
            throw new MtonsException("请先在后台设置又拍云配置信息");
        }

        if (!src.startsWith("/")) {
            src = "/" + src;
        }

        if (!src.endsWith("/")) {
            src = src + "/";
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
        String domain = appContext.getConfig().get("upyun_oss_domain");
        String path = StringUtils.remove(storePath, domain);
        UpYun yun = builder();
        try {
            yun.deleteFile(path);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private UpYun builder() {
        String bucket = appContext.getConfig().get("upyun_oss_bucket");
        String operator = appContext.getConfig().get("upyun_oss_operator");
        String password = appContext.getConfig().get("upyun_oss_password");

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
