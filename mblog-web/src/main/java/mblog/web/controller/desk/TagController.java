/**
 *
 */
package mblog.web.controller.desk;

import mblog.core.data.Post;
import mblog.core.persist.service.PostService;
import mblog.web.controller.BaseController;
import mblog.web.controller.desk.Views;
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
    private PostService postService;

    @RequestMapping("/tag/{kw}")
    public String tag(@PathVariable String kw, ModelMap model) {
        Pageable pageable = wrapPageable();
        try {
            if (StringUtils.isNotEmpty(kw)) {
                Page<Post> page = postService.searchByTag(pageable, kw);
                model.put("page", page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.put("kw", kw);
        return view(Views.TAGS_TAG);
    }

}
