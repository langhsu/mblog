package com.mtons.mblog.shiro.tags;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.RoleTag}</p>
 */
public abstract class RoleTag extends SecureTag {
    String getName(Map params) {
        return getParam(params, "name");
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        boolean show = showBody(getName(params));
        if (show) {
            renderBody(env, body);
        }
    }

    protected abstract boolean showBody(String roleName);
}
