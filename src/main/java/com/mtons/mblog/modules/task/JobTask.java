package com.mtons.mblog.modules.task;

import com.mtons.mblog.base.storage.StorageFactory;
import com.mtons.mblog.base.utils.ResourceLock;
import com.mtons.mblog.modules.entity.Resource;
import com.mtons.mblog.modules.entity.PostResource;
import com.mtons.mblog.modules.repository.ResourceRepository;
import com.mtons.mblog.modules.repository.PostResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * auto clear pic
 *
 * @author saxing 2019/4/5 16:35
 */
@Component
@EnableScheduling
@Slf4j
public class JobTask {

    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    PostResourceRepository postResourceRepository;
    @Autowired
    protected StorageFactory storageFactory;

    @Scheduled(cron = "0 0 1 1/1 * ? ")
    public void del3DayAgoPic(){
        LocalDateTime now = LocalDateTime.now();
        log.info("开始清理图片");
        now = now.minusDays(3);
        String timeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);
        List<Resource> resources = resourceRepository.find0Before(timeStr);
        if (CollectionUtils.isNotEmpty(resources)){
            for (Resource resource : resources){
                String key = ResourceLock.getPicKey(resource.getId());
                AtomicInteger lock = ResourceLock.getAtomicInteger(key);
                synchronized (lock){
                    List<PostResource> postResource = postResourceRepository.findByResourceId(resource.getId());
                    if (CollectionUtils.isEmpty(postResource)){
                        storageFactory.get().deleteFile(resource.getPath());
                        resourceRepository.delete(resource);
                    }else{
                        log.error("图片计数错误， picId: {}", resource.getId());
                    }
                }
            }
        }
    }

}
