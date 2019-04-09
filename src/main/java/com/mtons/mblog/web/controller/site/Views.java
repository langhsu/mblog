/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site;

/**
 * 主题页面配置
 *
 * @author langhsu
 */
public interface Views {
    /**
     * 登录
     */
    String LOGIN = "/auth/login";

    /**
     * 注册
     */
    String REGISTER = "/auth/register";

    /**
     * 三方登录回调注册
     */
    String OAUTH_REGISTER = "/auth/oauth_register";

    /**
     * 找回密码
     */
    String FORGOT = "/auth/forgot";

    /**
     * 首页
     */
    String INDEX = "/index";

    String USER_METHOD_TEMPLATE = "/user/method_%s";

    /**
     * 用户文章列表
     */
    String METHOD_POSTS = "posts";

    /**
     * 用户评论列表
     */
    String METHOD_COMMENTS = "comments";

    /**
     * 用户收藏列表
     */
    String METHOD_FAVORITES = "favorites";

    /**
     * 用户消息列表
     */
    String METHOD_MESSAGES = "messages";

    /**
     * 个人-修改头像
     */
    String SETTINGS_AVATAR = "/settings/avatar";

    /**
     * 个人-修改密码
     */
    String SETTINGS_PASSWORD = "/settings/password";

    /**
     * 个人-修改基本信息
     */
    String SETTINGS_PROFILE = "/settings/profile";

    /**
     * 个人-修改邮箱
     */
    String SETTINGS_EMAIL = "/settings/email";

    /**
     * 标签列表
     */
    String TAG_INDEX = "/tag/index";

    /**
     * 标签文章列表
     */
    String TAG_VIEW = "/tag/view";

    /**
     * 搜索
     */
    String SEARCH = "/search";

    /**
     * 编辑文章
     */
    String POST_EDITING = "/channel/editing";

    /**
     * 文章列表
     */
    String POST_INDEX = "/channel/index";

    /**
     * 文章详情
     */
    String POST_VIEW = "/channel/view";

    String REDIRECT_USER_HOME = "redirect:/users/%d";
    String REDIRECT_INDEX = "redirect:/index";
}
