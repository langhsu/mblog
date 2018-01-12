package mblog.template.directive;

import mblog.core.data.AuthMenu;
import mblog.core.persist.service.AuthMenuService;
import mblog.template.DirectiveHandler;
import mblog.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by langhsu on 2017/11/21.
 */
@Component
public class AuthcDirective extends TemplateDirective {
    @Autowired
    private AuthMenuService authMenuService;

    @Override
    public String getName() {
        return "authc";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long pid = handler.getInteger("pid", 0);

        List<AuthMenu> list = authMenuService.findByParentId(pid);
        handler.put(RESULTS, list).render();
    }

}
