package com.mtons.mblog.modules.entity;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "path", columnDefinition = "varchar(200) NOT NULL DEFAULT ''")
    private String path;

    @Column(name = "count", columnDefinition = "bigint(20) NOT NULL DEFAULT '0'")
    private long count;

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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
