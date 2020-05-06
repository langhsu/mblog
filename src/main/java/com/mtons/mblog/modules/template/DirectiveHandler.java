package com.mtons.mblog.modules.template;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

/**
 * Created by langhsu on 2017/11/14.
 */
public class DirectiveHandler {
    private Environment env;
    private Map<String, TemplateModel> parameters;
    private TemplateModel[] loopVars;
    private TemplateDirectiveBody body;
    private Environment.Namespace namespace;

    /**
     * 构建 DirectiveHandler
     *
     * @param env
     * @param parameters
     * @param loopVars
     * @param body
     */
    public DirectiveHandler(Environment env, Map<String, TemplateModel> parameters, TemplateModel[] loopVars,
                            TemplateDirectiveBody body) {
        this.env = env;
        this.loopVars = loopVars;
        this.parameters = parameters;
        this.body = body;
        this.namespace = env.getCurrentNamespace();
    }

    public void render() throws IOException, TemplateException {
        Assert.notNull(body, "must have template directive body");
        body.render(env.getOut());
    }

    public void renderString(String text) throws Exception {
        env.getOut().write(text);
    }

    public Environment getEnv() {
        return env;
    }

    public String bodyResult() throws IOException, TemplateException {
        if (body == null) {
            return "";
        }

        StringWriter writer = new StringWriter();
        body.render(writer);
        return writer.toString();
    }

    public DirectiveHandler put(String key, Object value) throws TemplateModelException {
        namespace.put(key, wrap(value));
        return this;
    }

    public String getString(String name) throws TemplateModelException {
        return TemplateModelUtils.converString(getModel(name));
    }

    public Integer getInteger(String name) throws TemplateModelException {
        return TemplateModelUtils.converInteger(getModel(name));
    }

    public Short getShort(String name) throws TemplateModelException {
        return TemplateModelUtils.converShort(getModel(name));
    }

    public Long getLong(String name) throws TemplateModelException {
        return TemplateModelUtils.converLong(getModel(name));
    }

    public Double getDouble(String name) throws TemplateModelException {
        return TemplateModelUtils.converDouble(getModel(name));
    }

    public String[] getStringArray(String name) throws TemplateModelException {
        return TemplateModelUtils.converStringArray(getModel(name));
    }

    public Boolean getBoolean(String name) throws TemplateModelException {
        return TemplateModelUtils.converBoolean(getModel(name));
    }

    public Date getDate(String name) throws TemplateModelException {
        return TemplateModelUtils.converDate(getModel(name));
    }

    public String getString(String name, String defaultValue) throws Exception {
        String result = getString(name);
        return null == result ? defaultValue : result;
    }

    public Integer getInteger(String name, int defaultValue) throws Exception {
        Integer result = getInteger(name);
        return null == result ? defaultValue : result;
    }

    public Long getLong(String name, long defaultValue) throws Exception {
        Long result = getLong(name);
        return null == result ? defaultValue : result;
    }


    public String getContextPath() {
        String ret = null;
        try {
            ret =  TemplateModelUtils.converString(getEnvModel("base"));
        } catch (TemplateModelException e) {
        }
        return ret;
    }

    /**
     * 包装对象
     * @param object
     * @return
     * @throws TemplateModelException
     */
    public TemplateModel wrap(Object object) throws TemplateModelException {
        return env.getObjectWrapper().wrap(object);
    }

    /**
     * 获取局部变量
     * @param name
     * @return
     * @throws TemplateModelException
     */
    public TemplateModel getEnvModel(String name) throws TemplateModelException {
        return env.getVariable(name);
    }

    private TemplateModel getModel(String name) {
        return parameters.get(name);
    }

}
