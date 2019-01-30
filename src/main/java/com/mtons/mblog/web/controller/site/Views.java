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
 * 
 * 返回页面配置
 * 
 * @author langhsu
 *
 */
public interface Views {
	String LOGIN = "/auth/login";
	String REGISTER = "/auth/register";
	String REGISTER_RESULT = "/auth/result";
	String OAUTH_REGISTER = "/auth/oauth_register";

	String FORGOT = "/auth/forgot";

	String INDEX = "/index";

	String USER_POSTS = "/user/method_posts";
	String USER_COMMENTS = "/user/method_comments";
	String USER_FAVORITES = "/user/method_favorites";
	String USER_MESSAGES = "/user/method_messages";

	String REDIRECT_USER_HOME = "redirect:/users/%d";

	String SETTINGS_AVATAR = "/settings/avatar";
	String SETTINGS_PASSWORD = "/settings/password";
	String SETTINGS_PROFILE = "/settings/profile";
	String SETTINGS_EMAIL = "/settings/email";

	String TAGS_TAG = "/tag";
	
	String BROWSE_SEARCH = "/search";

	String ROUTE_POST_EDITING = "/channel/editing";
	String ROUTE_POST_INDEX = "/channel/index";
	String ROUTE_POST_VIEW = "/channel/view";
}
