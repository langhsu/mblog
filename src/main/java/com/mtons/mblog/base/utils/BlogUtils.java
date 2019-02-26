package com.mtons.mblog.base.utils;

import com.alibaba.fastjson.JSON;
import com.mtons.mblog.base.lang.Theme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : landy
 */
@Slf4j
public class BlogUtils {

    public static List<Theme> getThemes() {
        List<Theme> themes = null;
        try {
            themes = loadDirectory(ResourceUtils.getFile("classpath:").getPath(), "templates");
            String location = System.getProperty("site.location");
            if (null != location) {
                themes.addAll(loadDirectory(location, "storage/templates"));
            }
        } catch (Exception e) {
            log.error("load themes error {}", e.getMessage());
        }
        return themes;
    }

    private static List<Theme> loadDirectory(String root, String directoryName) throws IOException {
        Path directory = Paths.get(root).resolve(directoryName);
        List<Theme> rets = new ArrayList<>();
        Files.list(directory).filter(entry -> {
            String fileName = entry.getFileName().toString();
            return Files.isDirectory(entry) && !StringUtils.equals("__MACOSX", fileName) && !StringUtils.equals("admin", fileName);
        }).forEach(entry -> {
            try {
                Theme theme;
                Path about = Paths.get(entry.toString()).resolve("about.json");
                if (Files.exists(about)) {
                    StringBuilder json = new StringBuilder();
                    Files.readAllLines(about).forEach(json::append);
                    theme = JSON.parseObject(json.toString(), Theme.class);
                } else {
                    theme = new Theme();
                }
                theme.setName(entry.getFileName().toString());
                theme.setPath(entry.toString());
                rets.add(theme);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        return rets;
    }
}
