package com.mtons.mblog.web.menu;

import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 加载后台Menu菜单
 *
 * @author - langhsu
 * @create - 2018/5/18
 */
public class MenuJsonUtils {
    private static String config = "/scripts/menu.json";
    private static List<Menu> menus;

    /**
     * 将配置文件转换成 List 对象
     *
     * @return
     */
    private static synchronized List<Menu> loadJson() throws IOException {
        InputStream inStream = MenuJsonUtils.class.getResourceAsStream(config);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));

        StringBuilder json = new StringBuilder();
        String tmp;
        try {
            while ((tmp = reader.readLine()) != null) {
                json.append(tmp);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            reader.close();
            inStream.close();
        }

        List<Menu> menus = JSONArray.parseArray(json.toString(), Menu.class);
        return menus;
    }

    public static List<Menu> getMenus() {
        if (null == menus) {
            try {
                menus = loadJson();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return menus;
    }
}
