package com.mtons.mblog.modules.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限值
 * @author - langhsu on 2018/2/11
 */
@Entity
@Table(name = "shiro_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Permission implements Serializable {
    private static final long serialVersionUID = -5979636077639378677L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "parent_id", updatable = false)
    private long parentId;
    
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private int weight;

    @Version
    private Integer version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
