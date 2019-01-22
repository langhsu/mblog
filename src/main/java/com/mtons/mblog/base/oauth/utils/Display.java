package com.mtons.mblog.base.oauth.utils;

public enum Display {
    DEFAULT("default"), PAGE("page"), IFRAME("iframe"), POPUP("popup"), TOUCH("touch"), MOBILE("mobile"), TV("tv"), PAD("pad"),
    CLIENT("client"), APPONWEIBO("apponweibo"), WAP("wap");

    private String type;

    private Display(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
