package com.mtons.mblog.base.oauth.utils;

import com.mtons.mblog.base.oauth.APIConfig;


public class OathConfig {
    public static String getValue(String key) {
        return APIConfig.getInstance().getValue(key);
    }
}
