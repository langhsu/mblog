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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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

    public static String removeHideHtml(String html) {
        if (html == null)
            return null;
        return Jsoup.clean(html, (new Whitelist()).addTags("hide"));
    }

    /**
     * 获取文章中的img url
     * @param html 代码
     * @return string
     */
    public static List<String> extractImage(String html) {
        List<String> urls = new ArrayList<>();
        if (html == null)
            return urls;
        Document doc = Jsoup.parseBodyFragment(html);
        Elements images = doc.select("img");
        if (null != images) {
            for(Element el : images) {
                urls.add(el.attr("src"));
            }
        }
        return urls;
    }

}
