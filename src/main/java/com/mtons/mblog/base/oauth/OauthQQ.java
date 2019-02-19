package com.mtons.mblog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mtons.mblog.base.oauth.utils.EnumOauthTypeBean;
import com.mtons.mblog.base.oauth.utils.OathConfig;
import com.mtons.mblog.base.oauth.utils.OpenOauthBean;
import com.mtons.mblog.base.oauth.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

public class OauthQQ extends Oauth {
    private static final Logger LOGGER = LoggerFactory.getLogger(OauthQQ.class);
    private static final String AUTH_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    private static final String TOKEN_INFO_URL = "https://graph.qq.com/oauth2.0/me";
    private static final String USER_INFO_URL = "https://graph.qq.com/user/get_user_info";

    public static OauthQQ me() {
        return new OauthQQ();
    }

    public OauthQQ() {
        setClientId(OathConfig.getValue("openid_qq"));
        setClientSecret(OathConfig.getValue("openkey_qq"));
        setRedirectUri(OathConfig.getValue("redirect_qq"));
    }

    public String getAuthorizeUrl(String state) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl("https://graph.qq.com/oauth2.0/authorize", params);
    }

    public String getTokenByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());

        String token = TokenUtil.getAccessToken(super.doGet("https://graph.qq.com/oauth2.0/token", params));
        LOGGER.debug(token);
        return token;
    }

    public String getTokenInfo(String accessToken) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap();
        params.put("access_token", accessToken);

        String openid = TokenUtil.getOpenId(super.doGet("https://graph.qq.com/oauth2.0/me", params));
        LOGGER.debug(openid);
        return openid;
    }

    public String getUserInfo(String accessToken, String uid) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap();
        params.put("access_token", accessToken);
        params.put("oauth_consumer_key", getClientId());
        params.put("openid", uid);
        params.put("format", "json");

        String userinfo = super.doGet("https://graph.qq.com/user/get_user_info", params);
        LOGGER.debug(userinfo);
        return userinfo;
    }

    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        String accessToken = getTokenByCode(code);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String openId = getTokenInfo(accessToken);
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        JSONObject dataMap = JSON.parseObject(getUserInfo(accessToken, openId));
        dataMap.put("openid", openId);
        dataMap.put("access_token", accessToken);
        LOGGER.debug(JSON.toJSONString(dataMap));
        return dataMap;
    }


    public OpenOauthBean getUserBeanByCode(String code)
            throws Exception {
        OpenOauthBean openOauthBean = null;
        JSONObject userInfo = me().getUserInfoByCode(code);

        if (userInfo.getString("ret").equals("0")) {
            openOauthBean = new OpenOauthBean();
            String openid = userInfo.getString("openid");
            String accessToken = userInfo.getString("access_token");
            String nickname = userInfo.getString("nickname");
            String photoUrl = userInfo.getString("figureurl_2");

            openOauthBean.setOauthCode(code);
            openOauthBean.setAccessToken(accessToken);
            openOauthBean.setExpireIn("");
            openOauthBean.setOauthUserId(openid);
            openOauthBean.setOauthType(EnumOauthTypeBean.TYPE_QQ.getValue());
            openOauthBean.setUsername("QQ" + openid.getBytes().hashCode());
            openOauthBean.setNickname(nickname);
            openOauthBean.setAvatar(photoUrl);
        } else {
            throw new Exception();
        }
        return openOauthBean;
    }
}
