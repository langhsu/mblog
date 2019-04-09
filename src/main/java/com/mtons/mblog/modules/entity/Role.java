package com.mtons.mblog.modules.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -1153854616385727165L;

    public static int STATUS_NORMAL = 0;
    public static int STATUS_CLOSED = 1;

    public static String ROLE_ADMIN = "admin";

    public static long ADMIN_ID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, updatable = false, length = 32)
    private String name;

    @Column(length = 140)
    private String description;

    private int status;

    @Transient
    private List<Permission> permissions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
