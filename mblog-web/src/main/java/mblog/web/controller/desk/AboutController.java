/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.web.controller.desk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mblog.web.controller.BaseController;

/**
 * @author langhsu
 *
 */
@Controller
public class AboutController extends BaseController {
	
	@RequestMapping("/about")
	public String about() {
		return view("/about/about");
	}
	
	@RequestMapping("/joinus")
	public String joinus() {
		return view("/about/joinus");
	}
	
	@RequestMapping("/faqs")
	public String faqs() {
		return view("/about/faqs");
	}

}
