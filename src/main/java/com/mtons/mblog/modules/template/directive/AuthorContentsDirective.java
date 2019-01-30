/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 根据作者取文章列表
 *
 * @author langhsu
 *
 */
@Component
public class AuthorContentsDirective extends TemplateDirective {
    @Autowired
	private PostService postService;

	@Override
	public String getName() {
		return "author_contents";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        int pageNo = handler.getInteger("pageNo", 1);
        int size = handler.getInteger("size", Consts.PAGE_DEFAULT_SIZE);
        long userId = handler.getInteger("userId", 0);

        Pageable pageable = PageRequest.of(pageNo - 1, size);
        Page<PostVO> result = postService.pagingByAuthorId(pageable, userId);

        handler.put(RESULTS, result).render();
    }

}
