package com.mtons.mblog.base.utils;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.lang.Theme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工具类
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

    public static Result<String> uploadTheme(MultipartFile file) throws IOException {
        String location = System.getProperty("site.location");
        if (StringUtils.isBlank(location)) {
            return Result.failure("site.location未配置");
        }

        Path root = Paths.get(location).resolve("storage/templates");
        Path zip = root.resolve(file.getOriginalFilename());

        Path target = root.resolve(FileKit.getFilename(Objects.requireNonNull(file.getOriginalFilename())));

        if (Files.exists(target)) {
            return Result.failure("该主题已存在");
        }

        Files.createDirectory(target);

        FileUtils.writeByteArrayToFile(zip.toFile(), file.getBytes());
        ZipUtil.unzip(zip.toFile(), target.toFile());
        Files.delete(zip);
        return Result.success();
    }

    private static List<Theme> loadDirectory(String root, String directoryName) throws IOException {
        Path directory = Paths.get(root).resolve(directoryName);
        return Files.list(directory).filter(entry -> {
            String name = entry.getFileName().toString();
            return Files.isDirectory(entry) && !StringUtils.equals("__MACOSX", name) && !StringUtils.equals("admin", name);
        }).map(entry -> {
            Theme theme = null;
            try {
                Path about = entry.resolve("about.json");
                if (Files.exists(about)) {
                    StringBuilder json = new StringBuilder();
                    Files.readAllLines(about).forEach(json::append);
                    theme = JSON.parseObject(json.toString(), Theme.class);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            if (null == theme) {
                theme = new Theme();
            }
            theme.setName(entry.getFileName().toString());
            theme.setPath(entry.toString());
            return theme;
        }).collect(Collectors.toList());
    }
}
