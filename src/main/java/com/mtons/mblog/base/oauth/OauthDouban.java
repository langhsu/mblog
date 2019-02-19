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

public class OauthDouban extends Oauth {
    private static final Logger LOGGER = LoggerFactory.getLogger(OauthDouban.class);
    private static final String AUTH_URL = "https://www.douban.com/service/auth2/auth";
    private static final String TOKEN_URL = "https://www.douban.com/service/auth2/token";
    private static final String USER_INFO_URL = "https://api.douban.com/v2/user/~me";

    public static OauthDouban me() {
        return new OauthDouban();
    }

    public OauthDouban() {
        setClientId(OathConfig.getValue("openid_douban"));
        setClientSecret(OathConfig.getValue("openkey_douban"));
        setRedirectUri(OathConfig.getValue("redirect_douban"));
    }

    public String getAuthorizeUrl(String state) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl("https://www.douban.com/service/auth2/auth", params);
    }

    public String getTokenByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());
        String token = TokenUtil.getAccessToken(super.doPost("https://www.douban.com/service/auth2/token", params));
        LOGGER.debug(token);
        return token;
    }

    public JSONObject getUserInfo(String accessToken) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("Authorization", "Bearer " + accessToken);
        String userInfo = super.doGetWithHeaders("https://api.douban.com/v2/user/~me", params);
        JSONObject dataMap = JSON.parseObject(userInfo);
        LOGGER.debug(dataMap.toJSONString());
        return dataMap;
    }

    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        String accessToken = getTokenByCode(code);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        JSONObject dataMap = getUserInfo(accessToken);
        dataMap.put("access_token", accessToken);
        LOGGER.debug(JSON.toJSONString(dataMap));
        return dataMap;
    }


    public OpenOauthBean getUserBeanByCode(String code)
            throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        OpenOauthBean openOauthBean = null;
        JSONObject userInfo = me().getUserInfoByCode(code);

        openOauthBean = new OpenOauthBean();
        String openid = userInfo.getString("uid");
        String accessToken = userInfo.getString("access_token");
        String nickname = userInfo.getString("name");
        String photoUrl = userInfo.getString("large_avatar");

        openOauthBean.setOauthCode(code);
        openOauthBean.setAccessToken(accessToken);
        openOauthBean.setExpireIn("");
        openOauthBean.setOauthUserId(openid);
        openOauthBean.setOauthType(EnumOauthTypeBean.TYPE_DOUBAN.getValue());
        openOauthBean.setUsername("DB" + openid.getBytes().hashCode());
        openOauthBean.setNickname(nickname);
        openOauthBean.setAvatar(photoUrl);

        return openOauthBean;
    }
}
