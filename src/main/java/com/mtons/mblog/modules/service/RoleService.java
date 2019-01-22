package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.entity.Permission;
import com.mtons.mblog.modules.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author - langhsu on 2018/2/11
 */
public interface RoleService {
    /**
     * 分页查询角色
     * @param pageable 分页对象
     * @param name 角色名称，模糊匹配，如果为null则忽略改查询条件
     */
    Page<Role> paging(Pageable pageable, String name);

    /**
     * 查询所有活动角色
     * @return 角色列表
     */
    List<Role> list();

    Map<Long, Role> findByIds(Set<Long> ids);

    /**
     * 根据角色ID获得角色信息
     * @param id 角色ID
     * @return Role
     */
    Role get(long id);

    /**
     * 保存角色信息。如果角色存在，则更新其信息，如果角色不存在，则添加新角色
     * @param r 角色对象
     */
    void update(Role r, Set<Permission> permissions);

    /**
     * 删除角色，已被授权的角色不允许删除
     * @param id 角色ID
     * @return true/false
     */
    boolean delete(long id);

    /**
     * 激活、停用角色
     * @param id 角色ID
     * @param active true：激活，false：停用
     */
    void activate(long id, boolean active);

}
