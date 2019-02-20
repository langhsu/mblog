package com.mtons.mblog.modules.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : langhsu
 */
@Data
@Entity
@Table(name = "mto_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, updatable = false, length = 32)
    private String name;

    @Column(length = 128)
    private String thumbnail;

    private String description;

    private long latestPostId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updated;

    private int posts;
}
