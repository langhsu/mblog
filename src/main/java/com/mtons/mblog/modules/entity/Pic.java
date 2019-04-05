package com.mtons.mblog.modules.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 图片
 *
 * @author saxing 2019/4/3 21:24
 */
@Entity
@Table(name = "mto_pic",
        uniqueConstraints = {@UniqueConstraint(name = "md5key", columnNames = {"md5"})}
)
public class Pic implements Serializable {
    private static final long serialVersionUID = -2263990565349962964L;

    @Id
    private long id;

    @Column(name = "md5", columnDefinition = "varchar(50) NOT NULL DEFAULT ''")
    private String md5;

    @Column(name = "path", columnDefinition = "varchar(255) NOT NULL DEFAULT ''")
    private String path;

    @Column(name = "amount", columnDefinition = "bigint(20) NOT NULL DEFAULT '0'")
    private long amount;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
