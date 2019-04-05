package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.PostPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 文章图片 dao
 *
 * @author saxing 2019/4/5 8:09
 */
public interface PostPicRepository extends JpaRepository<PostPic, Long>, JpaSpecificationExecutor<PostPic> {

    int deleteByPostId(long postId);

}
