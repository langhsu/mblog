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

/**
 * @author langhsu on 2015/8/6.
 */
public interface UserEventService {
    /**
     * 自增发布文章数
     * @param userId
     * @param postId
     */
    void identityPost(Long userId, long postId, boolean identity);

    /**
     * 自增评论数
     * @param userId
     * @param commentId
     */
    void identityComment(Long userId, long commentId, boolean identity);

}
