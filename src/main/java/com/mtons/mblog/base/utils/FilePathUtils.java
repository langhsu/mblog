/**
 * 
 */
package com.mtons.mblog.base.utils;

import org.apache.commons.text.RandomStringGenerator;

/**
 * @author langhsu
 *
 */
public class FilePathUtils {
	private static final int[]  AVATAR_GRIDS = new int[] {3,3,3};
	private static final int    AVATAR_LENGTH = 9;

	private static final String Y = "/yyyy/";

	private static RandomStringGenerator randomString = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
	
	public static String getAvatar(long key) {
		String r = String.format("%09d", key);
		StringBuffer buf = new StringBuffer(32);
		
		int pos = 0;
		for (int t: AVATAR_GRIDS) {
			buf.append(r.substring(pos, pos + t));
			pos += t;
			if (pos < AVATAR_LENGTH) {
				buf.append('/');
			}
		}
        return buf.toString();
	}

	/**
	 * 生成路径和文件名
	 * 以当前时间开头加4位随机数的文件名
	 *
	 * @param originalFilename 原始文件名
	 * @return 10位长度文件名+文件后缀
	 */
	public static String wholePathName(String originalFilename, String key) {
		StringBuilder builder = new StringBuilder(52);
		builder.append("/_signature/");
		builder.append(key);
		builder.append(FileKit.getSuffix(originalFilename));
		return builder.toString();
	}

	public static String wholePathName(String basePath, String ext, String key) {
		return basePath + wholePathName(ext, key);
	}
	
	public static void main(String[] args) {
		String base = FilePathUtils.getAvatar(50);
		System.out.println(String.format("/%s_%d.jpg", base, 100));
		System.out.println(FilePathUtils.wholePathName("a.jpg", "123"));
	}
	
}
