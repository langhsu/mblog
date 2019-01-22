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
	 * 默认头像
	 */
	String AVATAR = "/dist/images/ava/default.png";
	
	/**
	 * 分隔符
	 */
	String SEPARATOR = ",";

	String ROLE_ADMIN = "admin";

	int IDENTITY_STEP = 1; // 自增步进

	int DECREASE_STEP = -1; // 递减

	int TIME_MIN = 1000; // 最小时间单位, 1秒

	// 忽略值
	int IGNORE = -1;

	int ZERO = 0;

	// 禁用状态
	int STATUS_CLOSED = 1;

	// 删除状态
	int STATUS_REMOVED = 2;

	/* 状态-初始 */
	int STATUS_NORMAL = 0;

	/* 状态-推荐 */
	int STATUS_FEATURED = 1;

	/* 状态-锁定 */
	int STATUS_LOCKED = 1;

	/**
	 * 排序
	 */
	interface order {
		String FEATURED = "featured";
		String NEWEST = "newest";
		String HOTTEST = "hottest";
		String FAVOR = "favors";
	}

	int VERIFY_BIND = 1;   // bind email
	int VERIFY_FORGOT = 2; // forgot password

	int VERIFY_STATUS_INIT = 0;      // 验证码-初始
	int VERIFY_STATUS_CERTIFIED = 1; // 验证码-已使用

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

	String EMAIL_TEMPLATE_CODE = "email_code.ftl";
}
