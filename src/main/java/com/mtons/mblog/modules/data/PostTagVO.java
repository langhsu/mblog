package com.mtons.mblog.modules.data;

import com.mtons.mblog.modules.entity.PostTag;
import lombok.Data;

/**
 * @author : langhsu
 */
@Data
public class PostTagVO extends PostTag {
    private PostVO post;
}
