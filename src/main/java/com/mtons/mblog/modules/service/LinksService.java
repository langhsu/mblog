package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.entity.Links;

import java.util.List;

/**
 * @author : landy
 * @version : 1.0
 * @date : 2019/11/6
 */
public interface LinksService {
    List<Links> findAll();
    void update(Links links);
    void delete(long id);
}
