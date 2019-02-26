package com.mtons.mblog.base.lang;

import lombok.Data;

import java.util.List;

/**
 * @author : landy
 */
@Data
public class Theme {
    /**
     * 所在路径
     */
    private String path;

    /**
     * 名称 (同目录名)
     */
    private String name;

    /**
     * 介绍
     */
    private String slogan;

    /**
     * 版本
     */
    private String version;

    /**
     * 作者
     */
    private String author;

    /**
     * 作者网站
     */
    private String website;

    /**
     * 预览图
     */
    private List<String> previews;
}
