package com.mtons.mblog.modules.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Date;
import java.util.List;

/**
 * Created by langhsu on 2017/11/14.
 */
public abstract class BaseMethod implements TemplateMethodModelEx {

    public String getString(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.converString(getModel(arguments, index));
    }

    public Integer getInteger(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.converInteger(getModel(arguments, index));
    }

    public Long getLong(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.converLong(getModel(arguments, index));
    }

    public Date getDate(List<TemplateModel> arguments, int index) throws TemplateModelException {
        return TemplateModelUtils.converDate(getModel(arguments, index));
    }

    public TemplateModel getModel(List<TemplateModel> arguments, int index) {
        if (index < arguments.size()) {
            return arguments.get(index);
        }
        return null;
    }
}
