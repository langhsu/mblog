/**
 * 
 */
package com.mtons.mblog.base.lang;


/**
 * @author langhsu
 *
 */
public interface Consts {
	/**
	 * 文件存储-缩略图目录
	 */
	String thumbnailPath = "/storage/thumbnails";

	/**
	 * 文件存储-头像目录
	 */
	String avatarPath = "/storage/avatars";

	/**
	 * 默认头像
	 */
	String AVATAR = "https://en.gravatar.com/userimage/154673030/b9a54b5b990a61cc074668b2e2a0b8c0.png";
	
	/**
	 * 分隔符
	 */
	String SEPARATOR = ",";

	String SEPARATOR_X = "x";

	String ROLE_ADMIN = "admin";

	int PAGE_DEFAULT_SIZE = 10;

	int IDENTITY_STEP = 1; // 自增步进

	int DECREASE_STEP = -1; // 递减

	int TIME_MIN = 1000; // 最小时间单位, 1秒

	// 忽略值
	int IGNORE = -1;

	int ZERO = 0;

	// 禁用状态
	int STATUS_CLOSED = 1;

	/* 状态-初始 */
	int STATUS_NORMAL = 0;

	/* 状态-推荐 */
	int STATUS_FEATURED = 1;

	/* 状态-锁定 */
	int STATUS_LOCKED = 1;

	int STATUS_HIDDEN = 1;

	/**
	 * 排序
	 */
	interface order {
		String FEATURED = "featured";
		String NEWEST = "newest";
		String HOTTEST = "hottest";
		String FAVOR = "favors";
	}

	int CODE_BIND = 1;   // bind email
	int CODE_FORGOT = 2; // forgot password
	int CODE_REGISTER = 3;

	int CODE_STATUS_INIT = 0;      // 验证码-初始
	int CODE_STATUS_CERTIFIED = 1; // 验证码-已使用

	int FEATURED_DEFAULT = 0; // 推荐状态-默认
	int FEATURED_ACTIVE = 1;  // 推荐状态-推荐

	/**
	 * 未读
	 */
	int UNREAD = 0;

	/**
	 * 已读
	 */
	int READED = 1;

	int MESSAGE_EVENT_FAVOR_POST = 1; // 有人喜欢了你的文章

	int MESSAGE_EVENT_COMMENT = 3; // 有人评论了你

	int MESSAGE_EVENT_COMMENT_REPLY = 4; // 有人回复了你

	String CACHE_USER = "userCaches";
	String CACHE_POST = "postCaches";

	/**
	 * 第三方回调配置
	 */
	String QQ_CALLBACK = "qq_callback"; // 第三方登录-QQ回调地址
	String QQ_APP_ID = "qq_app_id";			// QQ互联APP_ID
	String QQ_APP_KEY = "qq_app_key";		// QQ互联APP_KEY

	String WEIBO_CALLBACK = "weibo_callback"; // 第三方登录-微博回调地址
	String WEIBO_CLIENT_ID = "weibo_client_id";		// 微博应用CLIENT_ID
	String WEIBO_CLIENT_SERCRET = "weibo_client_sercret";	// 微博应用CLIENT_SERCRET

	String DOUBAN_CALLBACK = "douban_callback";	// 第三方登录-豆瓣回调地址
	String DOUBAN_API_KEY = "douban_api_key";		// 豆瓣API_KEY
	String DOUBAN_SECRET_KEY = "douban_secret_key";		// 豆瓣SECRET_KEY

	String GITHUB_CALLBACK = "github_callback";	// 第三方登录-github回调地址
	String GITHUB_CLIENT_ID = "github_client_id";//github应用CLIENT_ID
	String GITHUB_SECRET_KEY = "github_secret_key";//github应用SECRET_KEY

	String EMAIL_TEMPLATE_CODE = "email_code.ftl";

	String EDITOR_MARKDOWN = "markdown";

	String STORAGE_LIMIT_SIZE = "storage_limit_size";
	String STORAGE_MAX_WIDTH = "storage_max_width";

	String THUMBNAIL_POST_SIZE = "thumbnail_post_size";
}
