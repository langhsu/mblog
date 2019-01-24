/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.modules.service.CommentService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 *
 */
@Controller
public class AdminController {
    @Autowired
    private ChannelService channelService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

	@RequestMapping("/admin")
	public String index(HttpServletRequest request, ModelMap model) {
		pushSystemStatus(request, model);
		model.put("channelCount", channelService.count());
        model.put("postCount", postService.count());
        model.put("commentCount", commentService.count());
        model.put("userCount", userService.count());
		return "/admin/index";
	}
	
	private void pushSystemStatus(HttpServletRequest request, ModelMap model) {
        float freeMemory = (float) Runtime.getRuntime().freeMemory();
        float totalMemory = (float) Runtime.getRuntime().totalMemory();
        float usedMemory = (totalMemory - freeMemory);
        float memPercent = Math.round(freeMemory / totalMemory * 100) ;
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");

        model.addAttribute("freeMemory", freeMemory);
        model.addAttribute("totalMemory", totalMemory / 1024 / 1024);
        model.addAttribute("usedMemory", usedMemory / 1024 / 1024);
        model.addAttribute("memPercent", memPercent);
        model.addAttribute("os", os);
        model.addAttribute("javaVersion", javaVersion);
	}
}
