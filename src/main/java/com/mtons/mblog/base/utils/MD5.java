/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.utils;

import com.mtons.mblog.base.lang.MtonsException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5摘要
 */
public class MD5 {

	/**
	 * 对字符串进行Md5加密
	 *
	 * @param input 原文
	 * @return md5后的密文
	 */
	public static String md5(String input) {
		return md5(input.getBytes());
	}

	/**
	 * 对字符串进行Md5加密
	 *
	 * @param input 原文
	 * @param salt 随机数
	 * @return string
	 */
	public static String md5(String input, String salt) {
		if(StringUtils.isEmpty(salt)) {
			salt = "";
		}
		return md5(salt + md5(input));
	}

	/**
	 * 文件md5计算
	 *
	 * @param bytes
	 * @return
	 */
	public static String md5(byte[] bytes)  {
		byte[] code;
		try {
			code = MessageDigest.getInstance("md5").digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new MtonsException(e.getMessage());
		}
		BigInteger bi = new BigInteger(code);
		return bi.abs().toString(32).toUpperCase();
	}

}
