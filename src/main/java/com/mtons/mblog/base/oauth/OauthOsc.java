package com.mtons.mblog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mtons.mblog.base.oauth.utils.OathConfig;
import com.mtons.mblog.base.oauth.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

public class OauthOsc extends Oauth {
    private static final Logger LOGGER = Logger.getLogger(OauthOsc.class);
    private static final String AUTH_URL = "http://www.oschina.net/action/oauth2/authorize";
    private static final String TOKEN_URL = "http://www.oschina.net/action/openapi/token";
    private static final String USER_INFO_URL = "http://www.oschina.net/action/openapi/user";
    private static final String TWEET_PUB = "http://www.oschina.net/action/openapi/tweet_pub";

    public static OauthOsc me() {
        return new OauthOsc();
    }

    public OauthOsc() {
        setClientId(OathConfig.getValue("openid_osc"));
        setClientSecret(OathConfig.getValue("openkey_osc"));
        setRedirectUri(OathConfig.getValue("redirect_osc"));
    }

    public String getAuthorizeUrl(String state) throws UnsupportedEncodingException {
        Map params = new HashMap();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl("http://www.oschina.net/action/oauth2/authorize", params);
    }

    public String getTokenByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map params = new HashMap();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());
        String token = TokenUtil.getAccessToken(super.doGet("http://www.oschina.net/action/openapi/token", params));
        LOGGER.debug(token);
        return token;
    }

    public JSONObject getUserInfo(String accessToken) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map params = new HashMap();
        params.put("access_token", accessToken);
        String userInfo = super.doGet("http://www.oschina.net/action/openapi/user", params);
        JSONObject dataMap = JSON.parseObject(userInfo);
        LOGGER.debug(dataMap.toJSONString());
        return dataMap;
    }

    public JSONObject tweetPub(String accessToken, String msg) {
        Map params = new HashMap();
        params.put("access_token", accessToken);
        params.put("msg", msg);
        try {
            JSONObject dataMap = JSON.parseObject(super.doPost("http://www.oschina.net/action/openapi/tweet_pub", params));

            LOGGER.debug(dataMap);
        } catch (KeyManagementException e) {
            LOGGER.error(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        } catch (NoSuchProviderException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }

    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        String accessToken = getTokenByCode(code);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        JSONObject dataMap = getUserInfo(accessToken);
        dataMap.put("access_token", accessToken);
        LOGGER.debug(dataMap);
        return dataMap;
    }
}
