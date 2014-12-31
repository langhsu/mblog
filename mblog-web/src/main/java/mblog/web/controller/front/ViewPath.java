/**
 * 
 */
package mblog.web.controller.front;

/**
 * 
 * 返回页面配置
 * 
 * @author langhsu
 *
 */
public interface ViewPath {
	String LOGIN = "/login";
	String REG = "/reg";
	String REG_RESULT = "/reg_result";
	
	String INDEX = "/index";
	String HOME = "/home";
	String USER_HOME = "/user/home";
	String ACCOUNT_AVATER = "/account/avater";
	String ACCOUNT_PASSWORD = "/account/password";
	String ACCOUNT_PROFILE = "/account/profile";
	
	String BLOG_POST = "/blog/post_";
	String BLOG_UPLOAD = "/blog/upload";
	
	String BROWSE_EXPLORE = "/browse/explore";
	String BROWSE_TAG = "/browse/tag";
	String BROWSE_GALLERY = "/browse/gallery";
	String BROWSE_GALLERY_SNIPPET = "/browse/snippet";
	String BROWSE_SEARCH = "/browse/search";
	String BROWSE_DETAIL = "/browse/detail";
}
