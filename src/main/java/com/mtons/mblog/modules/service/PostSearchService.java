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
     * @param pageable 分页
     * @param q 关键字
     * @throws Exception
     */
    Page<PostVO> search(Pageable pageable, String q) throws Exception;

    /**
     * 搜索 Tag
     * @param pageable 分页
     * @param tag 关键字
     */
    Page<PostVO> searchByTag(Pageable pageable, String tag);

    /**
     * 重建
     */
    void resetIndexes();
}
