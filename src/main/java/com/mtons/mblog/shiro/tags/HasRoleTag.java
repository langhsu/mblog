package com.mtons.mblog.shiro.tags;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.HasRoleTag}</p>
 */
public class HasRoleTag extends RoleTag {
    protected boolean showBody(String roleName) {
        return getSubject() != null && getSubject().hasRole(roleName);
    }
}
