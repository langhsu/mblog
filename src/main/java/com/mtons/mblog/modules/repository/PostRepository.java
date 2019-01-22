/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author langhsu
 */
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    /**
     * 查询指定用户
     *
     * @param pageable
     * @param authorId
     * @return
     */
    Page<Post> findAllByAuthorIdOrderByCreatedDesc(Pageable pageable, long authorId);

    // findLatests
    List<Post> findTop10ByOrderByCreatedDesc();

    // findHots
    List<Post> findTop10ByOrderByViewsDesc();

    List<Post> findAllByIdIn(Collection<Long> id);

    List<Post> findTop5ByFeaturedGreaterThanOrderByCreatedDesc(int featured);

    @Query("select coalesce(max(weight), 0) from Post")
    int maxWeight();

    @Modifying
    @Transactional
    @Query("update Post set views = views + :increment where id = :id")
    void updateViews(@Param("id") long id, @Param("increment") int increment);

    @Modifying
    @Transactional
    @Query("update Post set favors = favors + :increment where id = :id")
    void updateFavors(@Param("id") long id, @Param("increment") int increment);

    @Modifying
    @Transactional
    @Query("update Post set comments = comments + :increment where id = :id")
    void updateComments(@Param("id") long id, @Param("increment") int increment);

}
