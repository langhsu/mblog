/**
 *
 */
package com.mtons.mblog.web.controller.site;

import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.PostSearchService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标签
 * @author langhsu
 *
 */
@Controller
public class TagController extends BaseController {
    @Autowired
    private PostSearchService postSearchService;

    @RequestMapping("/tag/{kw}")
    public String tag(@PathVariable String kw, ModelMap model) {
        Pageable pageable = wrapPageable();
        try {
            if (StringUtils.isNotEmpty(kw)) {
                Page<PostVO> page = postSearchService.searchByTag(pageable, kw);
                model.put("page", page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.put("kw", kw);
        return view(Views.TAGS_TAG);
    }

}
