/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.FavoriteVO;
import com.mtons.mblog.modules.service.FavoriteService;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 根据作者取收藏列表
 *
 * @author landy
 * @since 3.0
 */
@Component
public class AuthorFavoritesDirective extends TemplateDirective {
    @Autowired
	private FavoriteService favoriteService;

	@Override
	public String getName() {
		return "author_favorites";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        int pageNo = handler.getInteger("pageNo", 1);
        int size = handler.getInteger("size", Consts.PAGE_DEFAULT_SIZE);
        long userId = handler.getInteger("userId", 0);

        Pageable pageable = PageRequest.of(pageNo - 1, size);
        Page<FavoriteVO> result = favoriteService.pagingByOwnId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
