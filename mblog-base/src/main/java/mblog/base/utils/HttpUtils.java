/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.utils;

import java.io.IOException;
import java.util.Map;

import mblog.base.lang.MtonsException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 
 * @author langhsu
 *
 */
public class HttpUtils {

	public static HttpClient getClient() {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8"); 
		return client;
	}
	
	public static String post(String url, Map<String, String> params) throws HttpException, IOException {
		HttpClient client = getClient();
		
    	PostMethod post = new PostMethod(url);
    	
    	for (Map.Entry<String, String> p : params.entrySet()) {
    		post.addParameter(p.getKey(), p.getValue());
    	}
    	
    	int status = client.executeMethod(post);

    	if (status != HttpStatus.SC_OK) {
    		throw new MtonsException("该地址请求失败");
    	}
    	return post.getResponseBodyAsString();
	}
}
