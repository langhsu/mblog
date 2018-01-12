/**
 * 
 */
package mblog.base.lang;


/**
 * @author langhsu
 *
 */
public interface Consts {
	String MTONS_CONFIG = "/mtons.properties";

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

	/**
	 * 附件-存储-本地
	 */
	int ATTACH_STORE_LOCAL = 0;

	/**
	 * 附件-存储-网络
	 */
	int ATTACH_STORE_NETWORK = 1;

	String SYSTEM_VERSION = "system.version";

	int VERIFY_BIND = 1;   // bind email
	int VERIFY_FORGOT = 2; // forgot password

	int VERIFY_STATUS_INIT = 0;      // 验证码-初始
	int VERIFY_STATUS_TOKEN = 1;     // 验证码-已生成token
	int VERIFY_STATUS_CERTIFIED = 2; // 验证码-已使用

	int ACTIVE_EMAIL = 1; // 邮箱激活

	int FEEDS_TYPE_POST = 1; // 动态类型 - 发布文章

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

	int NOTIFY_EVENT_FAVOR_POST = 1; // 有人喜欢了你的文章

	int NOTIFY_EVENT_FOLLOW = 2; // 有人关注了你

	int NOTIFY_EVENT_COMMENT = 3; // 有人评论了你

	int NOTIFY_EVENT_COMMENT_REPLY = 4; // 有人回复了你

	String EMAIL_TEMPLATE_FORGOT = "forgot.ftl";
	String EMAIL_TEMPLATE_BIND = "bind.ftl";
}
