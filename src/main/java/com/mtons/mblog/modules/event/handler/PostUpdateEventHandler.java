package com.mtons.mblog.modules.event.handler;

import com.mtons.mblog.modules.event.PostUpdateEvent;
import com.mtons.mblog.modules.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author langhsu
 */
@Component
public class PostUpdateEventHandler implements ApplicationListener<PostUpdateEvent> {
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MessageService messageService;

    @Async
    @Override
    public void onApplicationEvent(PostUpdateEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getAction()) {
            case PostUpdateEvent.ACTION_PUBLISH:
                userEventService.identityPost(event.getUserId(), true);
                break;
            case PostUpdateEvent.ACTION_DELETE:
                userEventService.identityPost(event.getUserId(), false);
                favoriteService.deleteByPostId(event.getPostId());
                commentService.deleteByPostId(event.getPostId());
                tagService.deteleMappingByPostId(event.getPostId());
                messageService.deleteByPostId(event.getPostId());
                break;
        }
    }
}
