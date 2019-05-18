package com.mtons.mblog.modules.entity;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 图片
 *
 * @author saxing 2019/4/3 21:24
 */
@Data
@Entity
@Table(name = "mto_resource",
        uniqueConstraints = {@UniqueConstraint(name = "UK_MD5", columnNames = {"md5"})}
)
public class Resource implements Serializable {
    private static final long serialVersionUID = -2263990565349962964L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 签名
     */
    @Column(name = "md5", columnDefinition = "varchar(100) NOT NULL DEFAULT ''")
    private String md5;

    /**
     * 存储路径
     */
    @Column(name = "path", columnDefinition = "varchar(255) NOT NULL DEFAULT ''")
    private String path;

    /**
     * 引用次数
     */
    @Column(name = "amount", columnDefinition = "bigint(20) NOT NULL DEFAULT '0'")
    private long amount;

    @Column(name = "create_time")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createTime;

    @Column(name = "update_time", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Generated(GenerationTime.ALWAYS)
    private LocalDateTime updateTime;

}
