package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Pic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * pic dao
 *
 * @author saxing 2019/4/5 7:43
 */
public interface PicRepository extends JpaRepository<Pic, Long>, JpaSpecificationExecutor<Pic> {

    Pic findByMd5(String md5);
}
