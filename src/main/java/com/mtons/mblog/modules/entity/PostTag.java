package com.mtons.mblog.modules.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author : langhsu
 */
@Data
@Entity
@Table(name = "mto_post_tag", indexes = {
        @Index(name = "IK_TAG_ID", columnList = "tag_id")
})
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "tag_id")
    private long tagId;

    private long weight;

}
