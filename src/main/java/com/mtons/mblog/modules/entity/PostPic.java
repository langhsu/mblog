package com.mtons.mblog.modules.entity;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文章图片
 *
 * @author saxing 2019/4/3 22:39
 */
@Entity
@Table(name = "mto_post_pic")
public class PostPic implements Serializable {
    private static final long serialVersionUID = -2343406058301647253L;

    @Id
    private long id;

    @Column(name = "post_id", columnDefinition = "bigint(20) NOT NULL")
    private long postId;

    @Column(name = "pic_id", columnDefinition = "bigint(20) NOT NULL")
    private long picId;

    @Column(name = "sort", columnDefinition = "int(11) NOT NULL DEFAULT '0'")
    private int sort;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getPicId() {
        return picId;
    }

    public void setPicId(long picId) {
        this.picId = picId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
