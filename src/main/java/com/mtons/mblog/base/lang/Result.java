/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.lang;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author langhsu
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -1491499610244557029L;

    public static int SUCCESS = 0;
    public static int FAILURED = -1;

    private int code; // 处理状态：0: 成功
    private String message;
    private T data; // 返回数据
    private ArrayList<Button> links = new ArrayList<>();

    private Result(int code, String message) {
        this(code, message, null);
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 处理成功，并返回数据
     *
     * @param data
     * @return
     */
    public static <T> Result<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> Result<T> successMessage(String message) {
        return success(message, null);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(Result.SUCCESS, message, data);
    }

    public static <T> Result<T> failure(String message) {
        return failure(Result.FAILURED, message);
    }

    /**
     * 处理失败，并返回数据（一般为错误信息）
     *
     * @param code
     * @return
     */
    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message, null);
    }

    public boolean isOk() {
        return code == SUCCESS;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result addLink(String link, String text) {
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
        return "{code:\"" + code + "\", message:\"" + message + "\", data:\"" + (data != null ? data.toString() : "") + "\"}";
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
