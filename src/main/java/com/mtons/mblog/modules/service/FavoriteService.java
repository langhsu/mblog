package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.FavoriteVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收藏记录
 * @author langhsu
 */
public interface FavoriteService {
    /**
     * 查询用户收藏记录
     * @param pageable
     * @param ownId
     * @return
     */
    Page<FavoriteVO> pagingByOwnId(Pageable pageable, long ownId);

    void add(long userId, long postId);
    void delete(long userId, long postId);
    void deleteByPostId(long postId);
}
