package org.mybatis.generator.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Unicode编码转换
 * @author YunxiaoXie
 *
 */
public class UnicodeUtil {

	/**
	 * 汉字转换为Unicode.
	 * @param str
	 * @return
	 */
	public static final String StringToUnicode(String str) {
		if (StringUtils.isNotEmpty(str)) {
			char[] utfBytes = str.toCharArray();
			StringBuilder sb = new StringBuilder();
			for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
				String hexB = Integer.toHexString(utfBytes[byteIndex]);
				if (hexB.length() <= 2) {
					hexB = "00" + hexB;
				}
				sb.append("\\u" + hexB);
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * Unicode字符转换为汉字
	 * @param uniStr
	 * @return
	 */
	public static final String UnicodeToString(String uniStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = uniStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = uniStr.substring(start + 2, uniStr.length());
			} else {
				charStr = uniStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		System.out.println(UnicodeUtil.StringToUnicode("本地字典"));
		System.out.println(UnicodeUtil.UnicodeToString("\\u4e2d\\u56fd"));
	}
}
