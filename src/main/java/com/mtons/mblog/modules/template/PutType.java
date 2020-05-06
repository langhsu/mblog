package com.mtons.mblog.modules.template;

import java.io.IOException;
import java.io.Writer;

/**
 * Created on 2020/5/6.
 *
 * @author Landy
 * @since 1.0.0
 */
public enum PutType {
    APPEND {
        @Override
        public void write(Writer out, String bodyResult, String putContents) throws IOException {
            out.write(bodyResult);
            out.write(putContents);
        }
    },
    PREPEND {
        @Override
        public void write(Writer out, String bodyResult, String putContents) throws IOException {
            out.write(putContents);
            out.write(bodyResult);
        }
    },
    REPLACE {
        @Override
        public void write(Writer out, String bodyResult, String putContents) throws IOException {
            out.write(putContents);
        }
    };

    public abstract void write(Writer out, String bodyResult, String putContents) throws IOException;
}
