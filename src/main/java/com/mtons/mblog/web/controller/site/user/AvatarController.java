/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site.user;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.utils.FileKit;
import com.mtons.mblog.base.utils.FilePathUtils;
import com.mtons.mblog.base.utils.ImageUtils;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import com.mtons.mblog.web.controller.site.posts.UploadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/user")
public class AvatarController extends BaseController {
	@Autowired
	private UserService userService;

	@Value("${site.store.size:2}")
	private String storeSize;

	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public String view() {
		return view(Views.USER_AVATAR);
	}
	
	@PostMapping("/avatar")
	@ResponseBody
	public UploadController.UploadResult upload(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		UploadController.UploadResult result = new UploadController.UploadResult();
		AccountProfile profile = getProfile();

		// 检查空
		if (null == file || file.isEmpty()) {
			return result.error(UploadController.errorInfo.get("NOFILE"));
		}

		String fileName = file.getOriginalFilename();

		// 检查类型
		if (!FileKit.checkFileType(fileName)) {
			return result.error(UploadController.errorInfo.get("TYPE"));
		}

		// 检查大小
		if (file.getSize() > (Long.parseLong(storeSize) * 1024 * 1024)) {
			return result.error(UploadController.errorInfo.get("SIZE"));
		}

		// 保存图片
		try {
			String ava100 = Consts.avatarPath + getAvaPath(profile.getId(), 240);
			byte[] bytes = ImageUtils.screenshot(file, 240, 240);

			AccountProfile user = userService.updateAvatar(profile.getId(), ava100);
			putProfile(user);

			String path = storageFactory.get().writeToStore(bytes, ava100);
			result.ok(UploadController.errorInfo.get("SUCCESS"));
			result.setName(fileName);
			result.setType(getSuffix(fileName));
			result.setPath(path);
			result.setSize(file.getSize());
		} catch (Exception e) {
			result.error(UploadController.errorInfo.get("UNKNOWN"));
		}
		return result;
	}

	public String getAvaPath(long uid, int size) {
		String base = FilePathUtils.getAvatar(uid);
		return String.format("/%s_%d.jpg", base, size);
	}
}
