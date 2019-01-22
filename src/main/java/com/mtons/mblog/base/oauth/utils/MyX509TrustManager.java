package com.mtons.mblog.base.oauth.utils;

import java.security.cert.X509Certificate;

class MyX509TrustManager implements javax.net.ssl.X509TrustManager {
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws java.security.cert.CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws java.security.cert.CertificateException {
    }
}
