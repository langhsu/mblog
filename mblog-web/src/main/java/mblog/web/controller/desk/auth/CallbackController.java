package mblog.web.controller.desk.auth;

import com.mblog.api.oauth.APIConfig;
import com.mblog.api.oauth.OauthDouban;
import com.mblog.api.oauth.OauthQQ;
import com.mblog.api.oauth.OauthSina;
import com.mblog.api.oauth.util.OpenOauthBean;
import com.mblog.api.oauth.util.TokenUtil;
import mblog.base.context.AppContext;
import mblog.base.lang.Consts;
import mblog.base.lang.MtonsException;
import mblog.base.lang.SiteConfig;
import mblog.base.utils.FilePathUtils;
import mblog.base.utils.ImageUtils;
import mblog.core.data.OpenOauth;
import mblog.core.data.User;
import mblog.core.persist.service.OpenOauthService;
import mblog.core.persist.service.UserService;
import mblog.web.controller.BaseController;
import mblog.web.controller.desk.Views;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第三方登录回调
 *
 * @author langhsu on 2015/8/12.
 */
@Controller
@RequestMapping("/oauth/callback")
public class CallbackController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());

    private static final String SESSION_STATE = "_SESSION_STATE_";

    @Autowired
    private OpenOauthService openOauthService;
    @Autowired
    private UserService userService;

    @Autowired
    private AppContext appContext;

    /**
     * 跳转到微博进行授权
     * @param request
     * @param response
     * @author A蛋壳  2015年9月12日 下午3:05:54
     */
    @RequestMapping("/call_weibo")
    public void callWeibo(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try {
            APIConfig.getInstance().setOpenid_sina(appContext.getConfig().get(SiteConfig.WEIBO_CLIENT_ID));
            APIConfig.getInstance().setOpenkey_sina(appContext.getConfig().get(SiteConfig.WEIBO_CLIENT_SERCRET));
            APIConfig.getInstance().setRedirect_sina(appContext.getConfig().get(SiteConfig.SITE_OAUTH_WEIBO));

            String state = TokenUtil.randomState();
            request.getSession().setAttribute(SESSION_STATE, state);
            response.sendRedirect(OauthSina.me().getAuthorizeUrl(state));
        } catch (Exception e) {
            throw new MtonsException("跳转到微博授权接口时发生异常");
        }
    }

    /**
     * 微博回调
     *
     * @param code
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/weibo")
    public String callback4Weibo(String code, String state, HttpServletRequest request, ModelMap model) throws Exception {
        // --
        String session_state = (String) request.getSession().getAttribute(SESSION_STATE);
        // 取消了授权
        if (StringUtils.isBlank(state) || StringUtils.isBlank(session_state) || !state.equals(session_state) || StringUtils.isBlank(code)) {
            throw new MtonsException("缺少必要的参数");
        }
        request.getSession().removeAttribute(SESSION_STATE);
        // --

        OpenOauthBean openOauthBean = null;
        try {
            openOauthBean = OauthSina.me().getUserBeanByCode(code);
        } catch (Exception e) {
            throw new MtonsException("解析信息时发生错误");
        }

        OpenOauth openOauth = new OpenOauth();
        openOauth.setOauthCode(code);
        openOauth.setAccessToken(openOauthBean.getAccessToken());
        openOauth.setExpireIn(openOauthBean.getNickname());
        openOauth.setOauthUserId(openOauthBean.getOauthUserId());
        openOauth.setOauthType(openOauthBean.getOauthType());
        openOauth.setRefreshToken(openOauthBean.getRefreshToken());
        openOauth.setUsername(openOauthBean.getUsername());
        openOauth.setNickname(openOauthBean.getNickname());
        openOauth.setAvatar(openOauthBean.getAvatar());

        // 判断是否存在绑定此accessToken的用户
        OpenOauth thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());
        if (thirdToken == null) {
            model.put("open", openOauth);
            return view(Views.OAUTH_REGISTER);
        }
        String username = userService.get(thirdToken.getUserId()).getUsername();
        return login(username, thirdToken.getAccessToken(), request);
    }

    /**
     * 跳转到QQ互联授权界面
     * @param request
     * @param response
     * @author A蛋壳  2015年9月12日 下午3:28:21
     */
    @RequestMapping("/call_qq")
    public void callQQ(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try {
            APIConfig.getInstance().setOpenid_qq(appContext.getConfig().get(SiteConfig.QQ_APP_ID));
            APIConfig.getInstance().setOpenkey_qq(appContext.getConfig().get(SiteConfig.QQ_APP_KEY));
            APIConfig.getInstance().setRedirect_qq(appContext.getConfig().get(SiteConfig.SITE_OAUTH_QQ));

            String state = TokenUtil.randomState();
            request.getSession().setAttribute(SESSION_STATE, state);
            response.sendRedirect(OauthQQ.me().getAuthorizeUrl(state));
        } catch (Exception e) {
            throw new MtonsException("跳转到QQ授权接口时发生异常");
        }
    }

    /**
     * QQ回调
     *
     * @param code
     * @param request
     * @return
     */
    @RequestMapping("/qq")
    public String callback4QQ(String code, String state, HttpServletRequest request, ModelMap model) {
        // --
        String session_state = (String) request.getSession().getAttribute(SESSION_STATE);
        // 取消了授权
        if (StringUtils.isBlank(state) || StringUtils.isBlank(session_state) || !state.equals(session_state) || StringUtils.isBlank(code)) {
            throw new MtonsException("缺少必要的参数");
        }
        request.getSession().removeAttribute(SESSION_STATE);
        // --

        OpenOauthBean openOauthBean = null;
        try {
            openOauthBean = OauthQQ.me().getUserBeanByCode(code);
        } catch (Exception e) {
            throw new MtonsException("解析信息时发生错误");
        }

        OpenOauth openOauth = new OpenOauth();
        openOauth.setOauthCode(code);
        openOauth.setAccessToken(openOauthBean.getAccessToken());
        openOauth.setExpireIn(openOauthBean.getNickname());
        openOauth.setOauthUserId(openOauthBean.getOauthUserId());
        openOauth.setOauthType(openOauthBean.getOauthType());
        openOauth.setUsername(openOauthBean.getUsername());
        openOauth.setNickname(openOauthBean.getNickname());
        openOauth.setAvatar(openOauthBean.getAvatar());

        // 判断是否存在绑定此accessToken的用户
        OpenOauth thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());

        if (thirdToken == null) {
            model.put("open", openOauth);
            return view(Views.OAUTH_REGISTER);
        }
        String username = userService.get(thirdToken.getUserId()).getUsername();
        return login(username, thirdToken.getAccessToken(), request);
    }

    /**
     * 跳转到豆瓣授权界面
     * @param request
     * @param response
     * @author A蛋壳  2015年9月12日 下午3:09:39
     */
    @RequestMapping("/call_douban")
    public void callDouban(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try {
            APIConfig.getInstance().setOpenid_douban(appContext.getConfig().get(SiteConfig.DOUBAN_API_KEY));
            APIConfig.getInstance().setOpenkey_douban(appContext.getConfig().get(SiteConfig.DOUBAN_SECRET_KEY));
            APIConfig.getInstance().setRedirect_douban(appContext.getConfig().get(SiteConfig.SITE_OAUTH_DOUBAN));

            String state = TokenUtil.randomState();
            request.getSession().setAttribute(SESSION_STATE, state);
            response.sendRedirect(OauthDouban.me().getAuthorizeUrl(state));
        } catch (Exception e) {
            throw new MtonsException("跳转到豆瓣授权接口时发生异常");
        }
    }

    /**
     * 豆瓣回调
     * @param code
     * @param state
     * @param request
     * @param model
     * @author A蛋壳  2015年9月12日 下午5:32:51
     */
    @RequestMapping("/douban")
    public String callBack4Douban(String code, String state, HttpServletRequest request, ModelMap model){
        // --
        String session_state = (String) request.getSession().getAttribute(SESSION_STATE);
        // 取消了授权
        if (StringUtils.isBlank(state) || StringUtils.isBlank(session_state) || !state.equals(session_state) || StringUtils.isBlank(code)) {
            throw new MtonsException("缺少必要的参数");
        }
        request.getSession().removeAttribute(SESSION_STATE);
        // --

        OpenOauthBean openOauthBean = null;
        try {
            openOauthBean = OauthDouban.me().getUserBeanByCode(code);
        } catch (Exception e) {
            throw new MtonsException("解析信息时发生错误");
        }

        OpenOauth openOauth = new OpenOauth();
        openOauth.setOauthCode(code);
        openOauth.setAccessToken(openOauthBean.getAccessToken());
        openOauth.setExpireIn(openOauthBean.getNickname());
        openOauth.setOauthUserId(openOauthBean.getOauthUserId());
        openOauth.setOauthType(openOauthBean.getOauthType());
        openOauth.setUsername(openOauthBean.getUsername());
        openOauth.setNickname(openOauthBean.getNickname());
        openOauth.setAvatar(openOauthBean.getAvatar());

        // 判断是否存在绑定此accessToken的用户
        OpenOauth thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());

        if (thirdToken == null) {
            model.put("open", openOauth);
            return view(Views.OAUTH_REGISTER);
        }
        String username = userService.get(thirdToken.getUserId()).getUsername();
        return login(username, thirdToken.getAccessToken(), request);
    }

    /**
     * 执行第三方绑定
     * @param openOauth
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/bind_oauth")
    public String bindOauth(OpenOauth openOauth, HttpServletRequest request) throws Exception {
        OpenOauth thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());
        String username = openOauth.getUsername();

        // 已存在：提取用户信息，登录
        if (thirdToken != null) {
            username = userService.get(thirdToken.getUserId()).getUsername();
            // 不存在：注册新用户，并绑定此token，登录
        } else {
            User user = userService.getByUsername(username);
            if(user == null){
                User u = userService.register(wrapUser(openOauth));

                // ===将远程图片下载到本地===
                String ava100 = appContext.getAvaDir() + getAvaPath(u.getId(), 100);
                ImageUtils.download(openOauth.getAvatar(), fileRepoFactory.select().getRoot() + ava100);
                userService.updateAvatar(u.getId(), ava100);

                thirdToken = new OpenOauth();
                BeanUtils.copyProperties(openOauth, thirdToken);
                thirdToken.setUserId(u.getId());
                openOauthService.saveOauthToken(thirdToken);
            } else {
                username = user.getUsername();
            }
        }
        return login(username, openOauth.getAccessToken(), request);
    }

    /**
     * 执行登录请求
     *
     * @param username
     * @param request
     * @return
     */
    private String login(String username, String accessToken, HttpServletRequest request) {
        String ret = view(Views.LOGIN);

        if (StringUtils.isNotBlank(username)) {
            AuthenticationToken token = createToken(username, accessToken);

            try {
                SecurityUtils.getSubject().login(token);

                ret = Views.REDIRECT_USER;
            } catch (AuthenticationException e) {
                logger.error(e);
                if (e instanceof UnknownAccountException) {
                    throw new MtonsException("用户不存在");
                } else if (e instanceof LockedAccountException) {
                    throw new MtonsException("用户被禁用");
                } else {
                    throw new MtonsException("用户认证失败");
                }
            }
            return ret;
        }
        throw new MtonsException("登录失败！");
    }

    private User wrapUser(OpenOauth openOauth) {
        User user = new User();
        user.setUsername(openOauth.getUsername());
        user.setName(openOauth.getNickname());
        user.setPassword(openOauth.getAccessToken());

        user.setSource(openOauth.getOauthType());

        if (StringUtils.isNotBlank(openOauth.getAvatar())) {
            //FIXME: 这里使用网络路径，前端应做对应处理
            user.setAvatar(openOauth.getAvatar());
        } else {
            user.setAvatar(Consts.AVATAR);
        }
        return  user;
    }

    public String getAvaPath(long uid, int size) {
		String base = FilePathUtils.getAvatar(uid);
		return String.format("/%s_%d.jpg", base, size);
	}

}