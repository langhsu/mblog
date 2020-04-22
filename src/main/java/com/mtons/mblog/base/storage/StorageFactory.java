/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.storage;

import com.mtons.mblog.base.storage.impl.AliyunStorageImpl;
import com.mtons.mblog.base.storage.impl.NativeStorageImpl;
import com.mtons.mblog.base.storage.impl.QiniuStorageImpl;
import com.mtons.mblog.base.storage.impl.UpYunStorageImpl;
import com.mtons.mblog.config.SiteOptions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * created by langhsu
 * on 2019/1/21
 */
@Component
public class StorageFactory implements InitializingBean {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SiteOptions siteOptions;

    private Map<String, Storage> fileRepoMap = new HashMap<>();

    public boolean registry(String key, Storage storage) {
        if (fileRepoMap.containsKey(key)) {
            return false;
        }
        fileRepoMap.put(key, storage);
        return true;
    }

    public Storage get() {
        String scheme = siteOptions.getValue("storage_scheme");
        if (StringUtils.isBlank(scheme)) {
            scheme = "native";
        }
        return fileRepoMap.get(scheme);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        fileRepoMap.put("native", applicationContext.getBean(NativeStorageImpl.class));
        fileRepoMap.put("upyun", applicationContext.getBean(UpYunStorageImpl.class));
        fileRepoMap.put("aliyun", applicationContext.getBean(AliyunStorageImpl.class));
        fileRepoMap.put("qiniu", applicationContext.getBean(QiniuStorageImpl.class));
    }
}
