package com.mtons.mblog.base.storage;

import com.mtons.mblog.BootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BootApplication.class)
@RunWith(SpringRunner.class)
public class MinioStorageImplTest {
    @Autowired
    private StorageFactory factory;
    @Test
    public void minioRemove() {
        factory.get().deleteFile("http://47.108.114.116:9000/blogimage/2024/01/05/测试图片.jpg");
    }
}
