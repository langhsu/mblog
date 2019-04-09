package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Resource dao
 *
 * @author saxing 2019/4/5 7:43
 */
public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {

    Resource findByMd5(String md5);

    List<Resource> findByMd5In(List<String> md5);

    @Query(value = "SELECT * FROM mto_resource WHERE amount <= 0 AND update_time < :time ", nativeQuery = true)
    List<Resource> find0Before(@Param("time")String time);

    @Modifying
    @Query("update Resource set amount = amount + :increment where md5 in (:md5s)")
    int updateAmount(@Param("md5s") Collection<String> md5s, @Param("increment") long increment);

    @Modifying
    @Query("update Resource set amount = amount + :increment where id in (:ids)")
    int updateAmountByIds(@Param("ids") Collection<Long> md5s, @Param("increment") long increment);
}
