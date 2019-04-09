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

import com.mtons.mblog.base.lang.Consts;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Set;

/**
 * @author langhsu on 2015/8/6.
 */
public interface UserEventService {
    /**
     * 自增发布文章数
     * @param userId
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityPost(Long userId, boolean plus);

    /**
     * 自增评论数
     * @param userId
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityComment(Long userId, boolean plus);

    /**
     * 批量自动评论数
     * @param userIds
     * @param plus
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityComment(Set<Long> userIds, boolean plus);
}
