package com.mtons.mblog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mtons.mblog.base.oauth.utils.OathConfig;
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

public class OauthBaidu extends Oauth {
    private static final Logger LOGGER = LoggerFactory.getLogger(OauthBaidu.class);
    private static final String AUTH_URL = "https://openapi.baidu.com/oauth/2.0/authorize";
    private static final String TOKEN_URL = "https://openapi.baidu.com/oauth/2.0/token";
    private static final String USER_INFO_URL = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";

    public static OauthBaidu me() {
        return new OauthBaidu();
    }

    public OauthBaidu() {
        setClientId(OathConfig.getValue("openid_baidu"));
        setClientSecret(OathConfig.getValue("openkey_baidu"));
        setRedirectUri(OathConfig.getValue("redirect_baidu"));
    }

    public String getAuthorizeUrl(String state) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl("https://openapi.baidu.com/oauth/2.0/authorize", params);
    }

    public String getTokenByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());
        String token = TokenUtil.getAccessToken(super.doPost("https://openapi.baidu.com/oauth/2.0/token", params));
        LOGGER.debug(token);
        return token;
    }

    public String getUserInfo(String accessToken) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        return super.doPost("https://openapi.baidu.com/rest/2.0/passport/users/getInfo", params);
    }

    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        String accessToken = getTokenByCode(code);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        String userInfo = getUserInfo(accessToken);
        JSONObject dataMap = JSON.parseObject(userInfo);
        dataMap.put("access_token", accessToken);
        LOGGER.debug(JSON.toJSONString(dataMap));
        return dataMap;
    }
}
