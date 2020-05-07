package com.mtons.mblog.modules.template.layout;

import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.io.Writer;

/**
 * @since 4.0.0
 */
@Component
public class BlockDirective extends TemplateDirective {
    public static final String BLOCK_NAME_PARAMETER = "name";

    @Override
    public String getName() {
        return "layout.block";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String blockName = handler.getString(BLOCK_NAME_PARAMETER);
        PutType putType = getPutType(handler, blockName);
        String bodyResult = handler.bodyResult();

        Writer out = handler.getEnv().getOut();
        String putContents = getPutContents(handler, blockName);
        putType.write(out, bodyResult, putContents);
    }

    private PutType getPutType(DirectiveHandler handler, String blockName) throws TemplateException {
        SimpleScalar putTypeScalar = (SimpleScalar) handler.getEnvModel(getBlockTypeVarName(blockName));
        if (putTypeScalar == null) {
            return PutType.APPEND;
        }

        return PutType.valueOf(putTypeScalar.getAsString());
    }

    private String getPutContents(DirectiveHandler handler, String blockName) throws TemplateModelException {
        SimpleScalar putContentsModel = (SimpleScalar) handler.getEnvModel(getBlockContentsVarName(blockName));
        String putContents = "";
        if (putContentsModel != null) {
            putContents = putContentsModel.getAsString();
        }
        return putContents;
    }

    public static String getBlockContentsVarName(String blockName) {
        return PutDirective.PUT_DATA_PREFIX + blockName + ".contents";
    }

    public static String getBlockTypeVarName(String blockName) {
        return PutDirective.PUT_DATA_PREFIX + blockName + ".type";
    }
}
