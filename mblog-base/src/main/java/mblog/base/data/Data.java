/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author langhsu on 2015/8/15.
 */
public class Data implements Serializable {
    private static final long serialVersionUID = -1491499610244557029L;

    public static int CODE_SUCCESS = 0;
    public static int CODE_FAILURED = -1;

    public static String NOOP = "";

    private int code; // 处理状态：0: 成功
    private String message;
    private Object data; // 返回数据
    private ArrayList<Button> links = new ArrayList<>();


    private Data(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 处理成功，并返回数据
     * @param data
     * @return
     */
    public static final Data success(Object data){
        return new Data(Data.CODE_SUCCESS, "操作成功", data);
    }

    /**
     *
     * @param message
     * @return
     * @deprecated with 1.0.3
     */
    public static final Data success(String message){
        return new Data(Data.CODE_SUCCESS, message, null);
    }

    public static final Data success(String message, Object data){
        return new Data(Data.CODE_SUCCESS, message, data);
    }

    /**
     * 处理失败，并返回数据（一般为错误信息）
     * @param code
     * @return
     */
    public static final Data failure(int code, String message){
        return new Data(code, message, null);
    }

    public static final Data failure(String message){
        return failure(Data.CODE_FAILURED, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public Data addLink(String link, String text) {
        links.add(new Button(link, text));
        return this;
    }

    public ArrayList<Button> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Button> links) {
        this.links = links;
    }

    public String toString() {
        return "{code:\"" + code + "\", message:\"" + message + "\", data:\"" + (data != null ? data.toString():"") + "\"}";
    }

    public class Button {
        private String text;
        private String link;

        public Button(String link, String text) {
            this.link = link;
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
