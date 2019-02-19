package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * @author langhsu on 2018/8/12.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    List<Permission> findAllByParentId(int parentId, Sort sort);

    @Query(value = "select count(role_id) from shiro_role_permission where permission_id=:permId", nativeQuery = true)
    int countUsed(@Param("permId") long permId);

    @Query("select coalesce(max(weight), 0) from Permission")
    int maxWeight();
}
