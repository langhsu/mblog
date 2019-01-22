package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : langhsu
 * @version : 1.0
 * @date : 2019/1/18
 */
public interface PostSearchService {
    /**
     * 根据关键字搜索
     * @param pageable
     * @param q
     * @throws Exception
     */
    Page<PostVO> search(Pageable pageable, String q) throws Exception;

    /**
     * 搜索 Tag
     * @param pageable
     * @param tag
     */
    Page<PostVO> searchByTag(Pageable pageable, String tag);
    void resetIndexs();
}
