package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.BootApplication;
import com.mtons.mblog.modules.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * post service test
 *
 * @author saxing 2019/4/5 17:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class PostServiceImplTest {

    @Autowired
    PostServiceImpl postService;

    @Test
    public void cleanPostPic() {
    }
}