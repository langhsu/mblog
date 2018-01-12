/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.service;

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

    /**
     * 自增关注数
     * @param userId
     * @param followId
     */
    void identityFollow(Long userId, long followId, boolean identity);

    /**
     * 自增粉丝数
     * @param userId
     * @param fansId
     */
    void identityFans(Long userId, long fansId, boolean identity);

    /**
     * 自增收藏数
     * @param userId
     * @param targetType
     * @param targetId
     */
    void identityFavors(Long userId, boolean identity, int targetType, long targetId);
}
