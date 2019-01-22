package com.mtons.mblog.base.upload;

import com.mtons.mblog.base.context.AppContext;
import com.mtons.mblog.base.upload.impl.AliyunFileRepoImpl;
import com.mtons.mblog.base.upload.impl.NativeFileRepoImpl;
import com.mtons.mblog.base.upload.impl.UpYunFileRepoImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by langhsu
 * on 2019/1/21
 */
@Component
public class FileRepoFactory {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AppContext appContext;

    private Map<String, FileRepo> fileRepoMap = new HashMap<>();

    @PostConstruct
    public void init() {
        fileRepoMap.put("native", applicationContext.getBean(NativeFileRepoImpl.class));
        fileRepoMap.put("upyun", applicationContext.getBean(UpYunFileRepoImpl.class));
        fileRepoMap.put("aliyun", applicationContext.getBean(AliyunFileRepoImpl.class));
    }

    public FileRepo get() {
        String scheme = appContext.getConfig().get("store_scheme");
        if (StringUtils.isBlank(scheme)) {
            scheme = "native";
        }
        return fileRepoMap.get(scheme);
    }
}
