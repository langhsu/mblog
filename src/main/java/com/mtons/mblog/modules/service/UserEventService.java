/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;

/**
 * @author langhsu on 2015/8/6.
 */
@CacheConfig(cacheNames = "userCaches")
public interface UserEventService {
    /**
     * 自增发布文章数
     * @param userId
     * @param postId
     */
    @CacheEvict(key = "#userId")
    void identityPost(Long userId, long postId, boolean identity);

    /**
     * 自增评论数
     * @param userId
     * @param commentId
     */
    @CacheEvict(key = "#userId")
    void identityComment(Long userId, long commentId, boolean identity);

}
