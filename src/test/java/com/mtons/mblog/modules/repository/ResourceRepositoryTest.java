package com.mtons.mblog.modules.repository;

import com.mtons.mblog.BootApplication;
import com.mtons.mblog.modules.entity.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * pic repo test
 *
 * @author saxing 2019/4/5 17:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class ResourceRepositoryTest {

    @Autowired
    ResourceRepository resourceRepository;

    @Test
    public void find0Before() {

        LocalDateTime now = LocalDateTime.now();
        String timeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);
        List<Resource> beforeResources = resourceRepository.find0Before(timeStr);
        System.out.println(beforeResources);
    }
}