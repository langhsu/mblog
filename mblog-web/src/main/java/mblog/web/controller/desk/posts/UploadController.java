package mblog.web.controller.desk.posts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mblog.base.data.Data;
import mblog.web.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片
 *
 * @author langhsu
 */
@Controller
@RequestMapping("/post")
public class UploadController extends BaseController {
    private static Map<String, String> errors = new HashMap<String, String>();

    // 文件允许格式

    private String[] allowFiles = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};

    static {
        errors.put("SUCCESS", "SUCCESS"); //默认成功

        errors.put("NOFILE", "未包含文件上传域");
        errors.put("TYPE", "不允许的文件格式");
        errors.put("SIZE", "文件大小超出限制");
        errors.put("REQUEST", "上传请求异常");
        errors.put("UNKNOWN", "未知错误");
    }

    @RequestMapping("/upload")
    public
    @ResponseBody
    Data upload(@RequestParam(value = "file", required = false) MultipartFile file, Boolean scale, Integer size) {
        Data data = Data.failure("error");

        if (file == null || file.isEmpty()) {
            data = Data.failure(errors.get("NOFILE"));
            return data;
        }

        if (!checkFileType(file.getOriginalFilename())) {
            data = Data.failure(errors.get("TYPE"));
            return data;
        }

        try {
            String path;

            if (scale != null && scale == true) {
                path = fileRepoFactory.select().tempScale(file, appContext.getTempDir(), size);
            } else {
                path = fileRepoFactory.select().temp(file, appContext.getTempDir());
            }
            data = Data.success("", path);
        } catch (Exception e) {
            //FIXME: error handle

            data = Data.failure(errors.get("UNKNOWN"));
        }

        return data;
    }

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
