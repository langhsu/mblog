package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.data.PermissionTree;
import com.mtons.mblog.modules.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author - langhsu on 2018/2/11
 */
public interface PermissionService {
    /**
     * 分页查询权限
     * @param pageable 分页对象
     * @param name 权限名称，模糊匹配，如果为null则忽略改查询条件
     */
    Page<Permission> paging(Pageable pageable, String name);

    /**
     * 列出所有菜单项
     * @return 菜单列表
     */
    List<PermissionTree> tree();

    /**
     * 查询子菜单项
     * @param parentId 根目录ID
     * @return 菜单列表
     */
    List<PermissionTree> tree(int parentId);

    /**
     * 查询所有权限
     * @return 权限列表
     */
    List<Permission> list();

    /**
     * 根据权限项ID获得权限项信息
     * @param id 权限ID
     * @return Permission
     */
    Permission get(long id);

}
