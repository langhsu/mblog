package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author langhsu on 2015/8/31.
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long>, JpaSpecificationExecutor<Favorite> {
    /**
     * 指定查询
     *
     * @param ownId
     * @param postId
     * @return
     */
    Favorite findByOwnIdAndPostId(long ownId, long postId);
    Page<Favorite> findAllByOwnIdOrderByCreatedDesc(Pageable pageable, long ownId);
}
