package com.bazl.hslims.utils;

public class DataFormat {
	/**
	 * 将double型数据格式化为科学计数法，返回值为String类型
	 * 
	 * @param number
	 *            源数据
	 * @param totalLength
	 *            格式化后的总长度(整数部分长度+小数部分长度)
	 * @param integerLength
	 *            格式化后整数部分长度(0表示结果以"0.xxEx"格式返回，整数部分始终为0)
	 * @param fixed
	 *            格式化后总长度是否为固定值(true表示长度固定，用0填充；false表示长度可变)
	 * @return 科学计数法
	 */
	public static String formatDecimal(double number, int totalLength,
			int integerLength, boolean fixed) {
		if (totalLength < 0 || integerLength < 0 || totalLength < integerLength)
			return null;

		StringBuffer pattern = new StringBuffer();
		// if integer-length is 0, then the decimal is formated to "0.xxxEx"
		if (integerLength == 0)
			pattern.append("'0'");
		// else create integer part with the given length
		for (int i = 0; i < integerLength; i++)
			pattern.append("0");
		// add symbol "."
		if (totalLength > integerLength)
			pattern.append(".");
		// create decimal part with the given length ( = total-length minus
		// integer-length)
		for (int i = 0; i < totalLength - integerLength; i++)
			pattern.append(fixed ? "0" : "#");
		// create the power part
		pattern.append("E0");

		java.text.DecimalFormat formator = new java.text.DecimalFormat(pattern
				.toString());
		return formator.format(number);
	}

	/**
	 * 
	 * @param str
	 *            累计PI值
	 * @return 累计PI值得科学技术发
	 */
	public static  String StringChange(String str) {
		String[] a = str.split("\\.");
		String RetrurnString = null;
		if (a.length > 1) {
			if (a[1].indexOf("E") != -1) {
				if (a[0].length() > 1) {
					RetrurnString = a[0].substring(0, 1)
							+ "."
							+ a[0].substring(1, 2)
							+ "E"
							+ ((a[0].length() - 1) + Integer.parseInt(a[1]
									.split("E")[1]));
				} else {
					RetrurnString = str.substring(0, 3) + "E"
							+ a[1].split("E")[1];
				}
			} else {
				if (a[0].length() > 1) {
					RetrurnString = a[0].substring(0, 1) + "."
							+ a[0].substring(1, 2) + "E" + (a[0].length() - 1);
				} else {
					RetrurnString = str.substring(0, 3);
				}
			}
		} else {
			RetrurnString = "0";
		}
		return RetrurnString;
	}

	public static void main(String args[]) {
		double a = 1.676788778;
		System.out.println(formatDecimal(a, 3, 1, false));
	}
}
