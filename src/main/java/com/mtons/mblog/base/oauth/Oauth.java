package com.mtons.mblog.base.oauth;

import com.mtons.mblog.base.oauth.utils.HttpKit;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

public class Oauth {
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    protected String getAuthorizeUrl(String authorize, Map<String, String> params) throws java.io.UnsupportedEncodingException {
        return HttpKit.initParams(authorize, params);
    }

    protected String doPost(String url, Map<String, String> params) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.post(url, params);
    }

    protected String doPost(String url, Map<String, String> params, Map<String, String> headers) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.post(url, params, headers);
    }

    protected String doGet(String url, Map<String, String> params) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.get(url, params);
    }

    protected String doGetWithHeaders(String url, Map<String, String> headers) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.get(url, null, headers);
    }

    public String getClientId() {
        return this.clientId;
    }

    public Oauth setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public Oauth setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public Oauth setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }
}
