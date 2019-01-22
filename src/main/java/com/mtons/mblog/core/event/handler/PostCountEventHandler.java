package com.mtons.mblog.core.event.handler;

import com.mtons.mblog.modules.service.UserEventService;
import com.mtons.mblog.core.event.PostUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 更新文章统计
 * created by langhsu
 */
@Component
public class PostCountEventHandler implements ApplicationListener<PostUpdateEvent> {
    @Autowired
    private UserEventService userEventService;

    @Async
    @Override
    public void onApplicationEvent(PostUpdateEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getAction()) {
            case PostUpdateEvent.ACTION_PUBLISH:
                userEventService.identityPost(event.getUserId(), event.getPostId(), true);
                break;
            case PostUpdateEvent.ACTION_DELETE:
                userEventService.identityPost(event.getUserId(), event.getPostId(), false);
                break;
        }
    }
}
