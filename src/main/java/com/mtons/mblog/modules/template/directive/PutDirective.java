package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.PutType;
import com.mtons.mblog.modules.template.TemplateDirective;
import freemarker.template.SimpleScalar;

import static com.mtons.mblog.modules.template.directive.BlockDirective.getBlockContentsVarName;
import static com.mtons.mblog.modules.template.directive.BlockDirective.getBlockTypeVarName;

/**
 * @since 4.0.0
 */
public class PutDirective extends TemplateDirective {
    public static final String PUT_DATA_PREFIX = PutDirective.class.getCanonicalName() + ".";
    public static final String PUT_BLOCK_NAME_PARAMETER = "block";
    public static final String PUT_TYPE_PARAMETER = "type";

    @Override
    public String getName() {
        return "layout.put";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String blockName = handler.getString(PUT_BLOCK_NAME_PARAMETER);
        String type = handler.getString(PUT_TYPE_PARAMETER);
        PutType putType = null;
        if (null != type) {
            putType = PutType.valueOf(type.toUpperCase());
        }
        if (putType == null) {
            putType = PutType.APPEND;
        }

        String bodyResult = handler.bodyResult();

        handler.getEnv().setVariable(getBlockContentsVarName(blockName), new SimpleScalar(bodyResult));
        handler.getEnv().setVariable(getBlockTypeVarName(blockName), new SimpleScalar(putType.name()));
    }

}
