package com.mtons.mblog.modules.data;

import lombok.Data;

import java.util.Date;

/**
 * @author : langhsu
 */
@Data
public class TagVO {
    private long id;

    private String name;

    private String thumbnail;

    private String description;

    private long latestPostId;

    private Date created;

    private Date updated;

    private int posts;

    private PostVO post;
}
