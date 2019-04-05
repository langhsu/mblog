package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Pic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * pic dao
 *
 * @author saxing 2019/4/5 7:43
 */
public interface PicRepository extends JpaRepository<Pic, Long>, JpaSpecificationExecutor<Pic> {

    Pic findByMd5(String md5);

    @Query(value = "SELECT * FROM mto_pic WHERE amount <= 0 AND update_time < :time ", nativeQuery = true)
    List<Pic> find0Before(@Param("time")String time);
}
