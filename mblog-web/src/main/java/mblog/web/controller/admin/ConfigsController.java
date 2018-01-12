/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.web.controller.admin;

import mblog.base.data.Data;
import mblog.base.lang.Consts;
import mblog.core.data.Config;
import mblog.core.persist.service.ConfigService;
import mblog.core.persist.service.ChannelService;
import mblog.core.persist.service.PostService;
import mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置
 *
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin/config")
public class ConfigsController extends BaseController {
	@Autowired
	private ConfigService configService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private PostService postService;

	@RequestMapping("/")
	public String list(ModelMap model) {
		model.put("configs", configService.findAll2Map());
		return "/admin/configs/main";
	}
	
	@RequestMapping("/update")
	@SuppressWarnings("unchecked")
	public String update(HttpServletRequest request, ModelMap model) {
		Map<String, String[]> params = request.getParameterMap();

		List<Config> configs = new ArrayList<>();

		params.forEach((k, v) -> {
			Config conf = new Config();
			conf.setKey(k);
			conf.setValue(v[0]);

			configs.add(conf);
		});
		configService.update(configs);
		return "redirect:/admin/config/";
	}
	
	@RequestMapping("/flush_conf")
	public @ResponseBody Data flushFiledia() {
		// 刷新系统变量
		List<Config> configs = configService.findAll();

		Map<String, String> configMap = new HashMap<>();
		configs.forEach(conf -> {
			servletContext.setAttribute(conf.getKey(), conf.getValue());
			configMap.put(conf.getKey(), conf.getValue());
		});

		appContext.setConfig(configMap);

		// 刷新文章Group
		servletContext.setAttribute("channels", channelService.findAll(Consts.STATUS_NORMAL));
		return Data.success("操作成功", Data.NOOP);
	}

	@RequestMapping("/flush_indexs")
	public @ResponseBody Data flushIndexs() {
		postService.resetIndexs();
		return Data.success("操作成功", Data.NOOP);
	}
	
}
