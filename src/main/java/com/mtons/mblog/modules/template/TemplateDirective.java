package com.mtons.mblog.modules.template;

import com.mtons.mblog.base.lang.Consts;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.Map;

/**
 * @author langhsu
 */
public abstract class TemplateDirective implements TemplateDirectiveModel {
    protected static String RESULT = "result";
    protected static String RESULTS = "results";

    @Override
    public void execute(Environment env, Map parameters,
                        TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            execute(new DirectiveHandler(env, parameters, loopVars, body));
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new TemplateException(e, env);
        }
    }

    public abstract String getName();

    public abstract void execute(DirectiveHandler handler) throws Exception;

    public PageRequest wrapPageable(DirectiveHandler handler) throws Exception {
        return wrapPageable(handler, Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * 包装分页对象
     *
     * @param handler DirectiveHandler
     * @param sort    排序对象
     * @return PageRequest
     * @throws Exception
     */
    public PageRequest wrapPageable(DirectiveHandler handler, Sort sort) throws Exception {
        int pageNo = handler.getInteger("pageNo", 1);
        int size = handler.getInteger("size", Consts.PAGE_DEFAULT_SIZE);
        if (null == sort) {
            return PageRequest.of(pageNo - 1, size);
        } else {
            return PageRequest.of(pageNo - 1, size, sort);
        }
    }
}
