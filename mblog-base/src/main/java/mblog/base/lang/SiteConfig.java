package mblog.base.lang;

/**
 * @author langhsu on 2015/9/5.
 */
public interface SiteConfig {
    /**
     * 站点类配置
     */

    String SITE_NAME = "site_name"; // 站点名称

    String SITE_DOMAIN   = "site_domain"; // 站点域名
    String SITE_KEYWORDS = "site_keywords"; // keywords
    String SITE_DESCRIPTION = "site_description"; // description
    String SITE_METAS = "site_metas"; // 扩展Metas

    String SITE_COPYRIGHT = "site_copyright";
    String SITE_ICP = "site_icp"; // 备案号
    
    /**
     * 第三方回调地址
     */
    String SITE_OAUTH_QQ = "site_oauth_qq"; // 第三方登录-QQ回调地址
    String SITE_OAUTH_WEIBO = "site_oauth_weibo"; // 第三方登录-微博回调地址
    String SITE_OAUTH_DOUBAN = "site_oauth_douban";	// 第三方登录-豆瓣回调地址
    String QQ_APP_ID = "qq_app_id";			// QQ互联APP_ID
    String QQ_APP_KEY = "qq_app_key";		// QQ互联APP_KEY
    String WEIBO_CLIENT_ID = "weibo_client_id";		// 微博应用CLIENT_ID
    String WEIBO_CLIENT_SERCRET = "weibo_client_sercret";	// 微博应用CLIENT_SERCRET
    String DOUBAN_API_KEY = "douban_api_key";		// 豆瓣API_KEY

    String DOUBAN_SECRET_KEY = "douban_secret_key";		// 豆瓣SECRET_KEY
}
