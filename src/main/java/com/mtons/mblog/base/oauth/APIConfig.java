package com.mtons.mblog.base.oauth;

import java.lang.reflect.Method;

public class APIConfig {
    private static APIConfig config = new APIConfig();

    private String openid_qq;

    private String openkey_qq;

    private String redirect_qq;

    private String openid_sina;

    private String openkey_sina;

    private String redirect_sina;

    private String openid_baidu;

    private String openkey_baidu;

    private String redirect_baidu;

    private String openid_renren;

    private String openkey_renren;

    private String redirect_renren;

    private String openid_osc;
    private String openkey_osc;
    private String redirect_osc;
    private String openid_douban;
    private String openkey_douban;
    private String redirect_douban;
    private String openid_github;
    private String openkey_github;
    private String redirect_github;
    private String lbs_ak_baidu;
    private String dp_key;
    private String dp_secret;

    private APIConfig() {

    }

    public static APIConfig getInstance() {
        if (config == null) {
            config = new APIConfig();
        }
        return config;
    }

    public String getValue(String attrName) {
        String firstLetter = attrName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + attrName.substring(1);
        Object value = "";
        try {
            Method method = APIConfig.class.getMethod(getter, new Class[0]);
            value = method.invoke(config, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) value;
    }

    public String getOpenid_qq() {
        return this.openid_qq;
    }

    public void setOpenid_qq(String openid_qq) {
        this.openid_qq = openid_qq;
    }

    public String getOpenkey_qq() {
        return this.openkey_qq;
    }

    public void setOpenkey_qq(String openkey_qq) {
        this.openkey_qq = openkey_qq;
    }

    public String getRedirect_qq() {
        return this.redirect_qq;
    }

    public void setRedirect_qq(String redirect_qq) {
        this.redirect_qq = redirect_qq;
    }

    public String getOpenid_sina() {
        return this.openid_sina;
    }

    public void setOpenid_sina(String openid_sina) {
        this.openid_sina = openid_sina;
    }

    public String getOpenkey_sina() {
        return this.openkey_sina;
    }

    public void setOpenkey_sina(String openkey_sina) {
        this.openkey_sina = openkey_sina;
    }

    public String getRedirect_sina() {
        return this.redirect_sina;
    }

    public void setRedirect_sina(String redirect_sina) {
        this.redirect_sina = redirect_sina;
    }

    public String getOpenid_baidu() {
        return this.openid_baidu;
    }

    public void setOpenid_baidu(String openid_baidu) {
        this.openid_baidu = openid_baidu;
    }

    public String getOpenkey_baidu() {
        return this.openkey_baidu;
    }

    public void setOpenkey_baidu(String openkey_baidu) {
        this.openkey_baidu = openkey_baidu;
    }

    public String getRedirect_baidu() {
        return this.redirect_baidu;
    }

    public void setRedirect_baidu(String redirect_baidu) {
        this.redirect_baidu = redirect_baidu;
    }

    public String getOpenid_renren() {
        return this.openid_renren;
    }

    public void setOpenid_renren(String openid_renren) {
        this.openid_renren = openid_renren;
    }

    public String getOpenkey_renren() {
        return this.openkey_renren;
    }

    public void setOpenkey_renren(String openkey_renren) {
        this.openkey_renren = openkey_renren;
    }

    public String getRedirect_renren() {
        return this.redirect_renren;
    }

    public void setRedirect_renren(String redirect_renren) {
        this.redirect_renren = redirect_renren;
    }

    public String getOpenid_osc() {
        return this.openid_osc;
    }

    public void setOpenid_osc(String openid_osc) {
        this.openid_osc = openid_osc;
    }

    public String getOpenkey_osc() {
        return this.openkey_osc;
    }

    public void setOpenkey_osc(String openkey_osc) {
        this.openkey_osc = openkey_osc;
    }

    public String getRedirect_osc() {
        return this.redirect_osc;
    }

    public void setRedirect_osc(String redirect_osc) {
        this.redirect_osc = redirect_osc;
    }

    public String getOpenid_douban() {
        return this.openid_douban;
    }

    public void setOpenid_douban(String openid_douban) {
        this.openid_douban = openid_douban;
    }

    public String getOpenkey_douban() {
        return this.openkey_douban;
    }

    public void setOpenkey_douban(String openkey_douban) {
        this.openkey_douban = openkey_douban;
    }

    public String getRedirect_douban() {
        return this.redirect_douban;
    }

    public void setRedirect_douban(String redirect_douban) {
        this.redirect_douban = redirect_douban;
    }

    public String getOpenid_github() {
        return this.openid_github;
    }

    public void setOpenid_github(String openid_github) {
        this.openid_github = openid_github;
    }

    public String getOpenkey_github() {
        return this.openkey_github;
    }

    public void setOpenkey_github(String openkey_github) {
        this.openkey_github = openkey_github;
    }

    public String getRedirect_github() {
        return this.redirect_github;
    }

    public void setRedirect_github(String redirect_github) {
        this.redirect_github = redirect_github;
    }

    public String getLbs_ak_baidu() {
        return this.lbs_ak_baidu;
    }

    public void setLbs_ak_baidu(String lbs_ak_baidu) {
        this.lbs_ak_baidu = lbs_ak_baidu;
    }

    public String getDp_key() {
        return this.dp_key;
    }

    public void setDp_key(String dp_key) {
        this.dp_key = dp_key;
    }

    public String getDp_secret() {
        return this.dp_secret;
    }

    public void setDp_secret(String dp_secret) {
        this.dp_secret = dp_secret;
    }
}
