package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author langhsu
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long>, JpaSpecificationExecutor<Favorite> {
    Favorite findByOwnIdAndPostId(long ownId, long postId);
    Page<Favorite> findAllByOwnId(Pageable pageable, long ownId);
    int deleteByPostId(long postId);
}
