/*
+--------------------------------------------------------------------------
|   WeCMS [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site.posts;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.utils.FileKit;
import com.mtons.mblog.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/**
 * Ueditor 文件上传
 *
 * @author langhsu
 */
@Controller
@RequestMapping("/post")
public class UploadController extends BaseController {
    public static HashMap<String, String> errorInfo = new HashMap<>();

    static {
        errorInfo.put("SUCCESS", "SUCCESS"); //默认成功
        errorInfo.put("NOFILE", "未包含文件上传域");
        errorInfo.put("TYPE", "不允许的文件格式");
        errorInfo.put("SIZE", "文件大小超出限制，最大支持2Mb");
        errorInfo.put("ENTYPE", "请求类型ENTYPE错误");
        errorInfo.put("REQUEST", "上传请求异常");
        errorInfo.put("IO", "IO异常");
        errorInfo.put("DIR", "目录创建失败");
        errorInfo.put("UNKNOWN", "未知错误");
    }

    @PostMapping("/upload")
    @ResponseBody
    public UploadResult upload(@RequestParam(value = "file", required = false) MultipartFile file,
                               HttpServletRequest request) throws IOException {
        UploadResult result = new UploadResult();
        String crop = request.getParameter("crop");
        int size = ServletRequestUtils.getIntParameter(request, "size", siteOptions.getIntegerValue(Consts.STORAGE_MAX_WIDTH));

        // 检查空
        if (null == file || file.isEmpty()) {
            return result.error(errorInfo.get("NOFILE"));
        }

        String fileName = file.getOriginalFilename();

        // 检查类型
        if (!FileKit.checkFileType(fileName)) {
            return result.error(errorInfo.get("TYPE"));
        }

        // 检查大小
        String limitSize = siteOptions.getValue(Consts.STORAGE_LIMIT_SIZE);
        if (StringUtils.isBlank(limitSize)) {
            limitSize = "2";
        }
        if (file.getSize() > (Long.parseLong(limitSize) * 1024 * 1024)) {
            return result.error(errorInfo.get("SIZE"));
        }

        // 保存图片
        try {
            String path;
            if (StringUtils.isNotBlank(crop)) {
                Integer[] imageSize = siteOptions.getIntegerArrayValue(crop, Consts.SEPARATOR_X);
                int width = ServletRequestUtils.getIntParameter(request, "width", imageSize[0]);
                int height = ServletRequestUtils.getIntParameter(request, "height", imageSize[1]);
                path = storageFactory.get().storeScale(file, Consts.thumbnailPath, width, height);
            } else {
                path = storageFactory.get().storeScale(file, Consts.thumbnailPath, size);
            }
            result.ok(errorInfo.get("SUCCESS"));
            result.setName(fileName);
            result.setPath(path);
            result.setSize(file.getSize());

        } catch (Exception e) {
            result.error(errorInfo.get("UNKNOWN"));
            e.printStackTrace();
        }

        return result;
    }

    public static class UploadResult {
        public static int OK = 200;
        public static int ERROR = 400;

        /**
         * 上传状态
         */
        private int status;

        /**
         * 提示文字
         */
        private String message;

        /**
         * 文件名
         */
        private String name;

        /**
         * 文件大小
         */
        private long size;

        /**
         * 文件存放路径
         */
        private String path;

        public UploadResult ok(String message) {
            this.status = OK;
            this.message = message;
            return this;
        }

        public UploadResult error(String message) {
            this.status = ERROR;
            this.message = message;
            return this;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }
}
