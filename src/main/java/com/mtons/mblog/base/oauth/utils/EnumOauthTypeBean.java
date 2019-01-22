package com.mtons.mblog.base.oauth.utils;

public enum EnumOauthTypeBean {
    TYPE_DOUBAN("豆瓣登录", 3),
    TYPE_QQ("QQ登录", 2),
    TYPE_SINA("微博登录", 1),
    TYPE_GITHUB("github登录", 4);

    private String description;
    private int value;

    private EnumOauthTypeBean(String desc, int value) {
        this.description = desc;
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumOauthTypeBean getEnumStatus(int type) throws Exception {
        EnumOauthTypeBean[] status = values();
        for (int i = 0; i < status.length; i++) {
            if (status[i].getValue() == type) {
                return status[i];
            }
        }

        throw new Exception();
    }
}
