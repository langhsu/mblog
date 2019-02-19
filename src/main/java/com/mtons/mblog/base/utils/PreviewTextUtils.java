/*
+--------------------------------------------------------------------------
|   Mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Created by langhsu
 */
public class PreviewTextUtils {
    /**
     * 提取纯文本
     * @param html 代码
     * @return string
     */
    public static String getText(String html) {
        if (html == null)
            return null;
        return Jsoup.clean(html, Whitelist.none()).trim();
    }

    /**
     * 提取纯文本
     * @param html 代码
     * @param length 提取文本长度
     * @return string
     */
    public static String getText(String html, int length){
        String text = getText(html);
        text = StringUtils.abbreviate(text, length);
        return text;
    }

    /**
     * 以下标签可以通过 (b, em, i, strong, u. 纯文本)
     * @param html 代码
     * @return string
     */
    public static String getSimpleHtml(String html) {
        if (html == null)
            return null;
        return Jsoup.clean(html, Whitelist.simpleText());
    }

}
