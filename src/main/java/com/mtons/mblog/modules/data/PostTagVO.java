package com.mtons.mblog.modules.data;

import lombok.Data;

/**
 * @author : langhsu
 */
@Data
public class PostTagVO {
    private long id;

    private long postId;

    private long tagId;

    private long weight;

    private PostVO post;
}
