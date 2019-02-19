package com.mtons.mblog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mtons.mblog.base.oauth.utils.OathConfig;
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

public class OauthRenren extends Oauth {
    private static final Logger LOGGER = LoggerFactory.getLogger(OauthRenren.class);
    private static final String AUTH_URL = "https://graph.renren.com/oauth/authorize";
    private static final String TOKEN_URL = "https://graph.renren.com/oauth/token";

    public static OauthRenren me() {
        return new OauthRenren();
    }

    public OauthRenren() {
        setClientId(OathConfig.getValue("openid_renren"));
        setClientSecret(OathConfig.getValue("openkey_renren"));
        setRedirectUri(OathConfig.getValue("redirect_renren"));
    }

    public String getAuthorizeUrl(String state, String display) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(display)) {
            params.put("display", display);
        }
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl("https://graph.renren.com/oauth/authorize", params);
    }

    private String getTokenByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());
        String token = super.doPost("https://graph.renren.com/oauth/token", params);
        LOGGER.debug(token);
        return token;
    }

    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        String tokenInfo = getTokenByCode(code);
        if (StringUtils.isBlank(tokenInfo)) {
            return null;
        }
        JSONObject json = JSONObject.parseObject(tokenInfo);
        String access_token = json.getString("access_token");
        if (StringUtils.isBlank(access_token)) {
            return null;
        }
        JSONObject userJson = json.getJSONObject("user");
        userJson.put("access_token", access_token);
        LOGGER.debug(JSON.toJSONString(userJson));
        return userJson;
    }
}
