package com.mtons.mblog;

import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author : xulang
 * @version : 1.0
 * @date : 2019/1/16
 */
public class Base64Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] keys = "mtons".getBytes("UTF-8");
        System.out.println(Base64Utils.encodeToString(Arrays.copyOf(keys, 16)));
    }
}
