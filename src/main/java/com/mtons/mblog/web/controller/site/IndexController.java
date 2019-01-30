/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.web.controller.site;

import javax.servlet.http.HttpServletRequest;

import com.mtons.mblog.base.lang.Consts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtons.mblog.web.controller.BaseController;

/**
 * @author langhsu
 *
 */
@Controller
public class IndexController extends BaseController{
	
	@RequestMapping(value= {"/", "/index"})
	public String root(ModelMap model, HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		model.put("order", order);
		model.put("pageNo", pageNo);
		return view(Views.INDEX);
	}

}
