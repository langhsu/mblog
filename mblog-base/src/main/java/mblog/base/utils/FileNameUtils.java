/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.utils;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author langhsu
 *
 */
public class FileNameUtils {
	private static String YYYYMM = "/yyyy/MMdd/";
	private static String DDHHMMSS = "ddHHmmss";
	
	private static String YYYYMMDDHHMMSS = "/yyyy/MMdd/ddHHmmss";
	
	/**
	 * 36个小写字母和数字
	 */
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	
	/**
	 * 生成当前年月日格式的文件路径
	 * 
	 * yyyyMM 200806
	 * 
	 * @return 结果字符串
	 */
	public static String genPathName() {
		return DateFormatUtils.format(new Date(), YYYYMM);
	}
	
	/**
	 * 生成文件名
	 * 以当前日、时间开头加4位随机数的文件名
	 * 
	 * ddHHmmss 03102230
	 * 
	 * @return 10位长度文件名
	 */
	public static String genFileName() {
		return DateFormatUtils.format(new Date(), DDHHMMSS) + RandomStringUtils.random(4, N36_CHARS);
	}

	/**
	 * 生成文件名
	 * 以当前时间开头加4位随机数的文件名
	 * 
	 * @param ext 文件名后缀，不带'.'
	 * @return 10位长度文件名+文件后缀
	 */
	public static String genFileName(String ext) {
		return genFileName() + "." + ext;
	}
	
	/**
	 * 生成路径和文件名
	 * 以当前时间开头加4位随机数的文件名
	 * 
	 * @param ext 文件名后缀，不带'.'
	 * @return 10位长度文件名+文件后缀
	 */
	public static String genPathAndFileName(String ext) {
		return DateFormatUtils.format(new Date(), YYYYMMDDHHMMSS) + RandomStringUtils.random(4, N36_CHARS) + "." + ext;
	}
	
}
