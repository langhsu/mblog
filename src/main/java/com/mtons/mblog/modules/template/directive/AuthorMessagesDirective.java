/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.MessageVO;
import com.mtons.mblog.modules.service.MessageService;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 查询用户消息列表
 *
 * @author landy
 * @since 3.0
 */
@Component
public class AuthorMessagesDirective extends TemplateDirective {
    @Autowired
	private MessageService messageService;

	@Override
	public String getName() {
		return "author_messages";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        int pageNo = handler.getInteger("pageNo", 1);
        int size = handler.getInteger("size", Consts.PAGE_DEFAULT_SIZE);
        long userId = handler.getInteger("userId", 0);

        Pageable pageable = PageRequest.of(pageNo - 1, size);
        Page<MessageVO> result = messageService.pagingByOwnId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
