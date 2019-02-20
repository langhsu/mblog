package com.mtons.mblog.modules.data;

import com.mtons.mblog.modules.entity.Tag;
import lombok.Data;

/**
 * @author : langhsu
 */
@Data
public class TagVO extends Tag {
    private PostVO post;
}
