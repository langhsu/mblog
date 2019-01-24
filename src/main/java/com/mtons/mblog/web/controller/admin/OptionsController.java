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

import com.mtons.mblog.base.data.Data;
import com.mtons.mblog.config.ContextStartup;
import com.mtons.mblog.modules.entity.Options;
import com.mtons.mblog.modules.service.OptionsService;
import com.mtons.mblog.modules.service.PostSearchService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统配置
 *
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin/options")
public class OptionsController extends BaseController {
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private PostSearchService postSearchService;
	@Autowired
	private ContextStartup contextStartup;

	@RequestMapping("/index")
	public String list(ModelMap model) {
		model.put("values", optionsService.findAll2Map());
		return "/admin/options/index";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, ModelMap model) {
		Map<String, String[]> params = request.getParameterMap();

		List<Options> options = new ArrayList<>();

		params.forEach((k, v) -> {
			Options conf = new Options();
			conf.setKey(k);
			conf.setValue(v[0]);

			options.add(conf);
		});

		optionsService.update(options);

		contextStartup.resetSetting(false);

		model.put("values", optionsService.findAll2Map());
		model.put("data", Data.success("操作成功", Data.NOOP));
		return "/admin/options/index";
	}

	/**
	 * 刷新系统变量
	 * @return
	 */
	@RequestMapping("/flush_conf")
	@ResponseBody
	public Data flushFiledia() {
		contextStartup.resetSetting(false);
		contextStartup.resetChannels();
		return Data.success("操作成功", Data.NOOP);
	}

	@RequestMapping("/flush_indexs")
	@ResponseBody
	public Data flushIndexs() {
		postSearchService.resetIndexs();
		return Data.success("操作成功", Data.NOOP);
	}
	
}
