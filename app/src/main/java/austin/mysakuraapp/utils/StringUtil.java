/**
 * @(#)UserAction.java	05/15/2014
 * 
 * Copyright (c) 2014 SAILSORS INSTRUMENTS LTD.,BEIJING.All rights reserved.
 * Created by 2014-05-15
 */
package austin.mysakuraapp.utils;

import android.content.Context;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import austin.mysakuraapp.R;

/**
 * 字符串工具类
 * 
 * @author 
 * 
 */
public class StringUtil {

	/**
	 * 将null值转为""
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String trimNull(String srcStr) {
		String result = "";
		if (!(null == srcStr || srcStr.equalsIgnoreCase("null"))) {
			result = srcStr;
		}
		return result;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true:表示空，false：表示不空
	 */
	public static boolean isEmpty(String str) {
		boolean flag = false;
		if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)
				|| str.trim().length() < 1)
			flag = true;
		return flag;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(Object str) {
		return str != null;
	}

	/**
	 * 使用对应的正则表达式对str进行校验
	 * 
	 * @param str
	 *            需要校验的字符串
	 * @param regEx
	 *            使用的正则表达式
	 * @return 符合规则返回true，否则false
	 */
	public static boolean checkStrWithRegex(String str, String regEx) {
		if (isEmpty(str))
			return false;

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 验证手机号码是否满足正则
	 * 
	 * @param phoneNum
	 * @return 满足返回true，否则返回false
	 */
	public static boolean isMobileNO(String phoneNum) {
		// return checkStrWithRegex(phoneNum, "^[1][358]\\d{9}$");
		return checkStrWithRegex(phoneNum,
				"^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
	}

	/**
	 * 验证银行卡号是否正确
	 * 
	 * @param creditCardNumbers
	 *            需要校验的银行卡号
	 * @return 满足返回true，否则返回false
	 */
	public static boolean isBankCardNum(String creditCardNumbers) {
		// 16到19个数字或字母
		return checkStrWithRegex(creditCardNumbers, "^[A-Za-z0-9]{16,19}$");
	}

	/**
	 * 判断是否是可以转化成数字的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumStr(String str) {
		return checkStrWithRegex(str, "^-?\\d+$");
	}

	// ////////////////////////////////////////以下为过时的///////////////////////////////////////////

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;

		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);

			if (sTemp.length() < 2)
				sb.append(0);

			sb.append(sTemp.toUpperCase());
		}

		return sb.toString();
	}

	/**
	 * 把16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();

		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}

		return result;
	}

	public static byte toByte(char c) {
		int p = "0123456789ABCDEF".indexOf(c);
		if (p == -1)
			p = "0123456789abcdef".indexOf(c);

		byte b = (byte) p;
		return b;
	}

	/**
	 * @param str
	 * @return
	 */
	public static String getXorValue(String str) {
		// TODO 等待优化

		StringBuilder sb = new StringBuilder();

		// 将字符串（每个字符串为一个16进制的整型值，以空格分隔）数组转换为整型数组
		// 例如："56 45 4E 01 08 00 58"
		String[] sArray = str.split(" ");
		int[] iArray = new int[sArray.length];
		for (int i = 0; i < sArray.length; i++) {
			// 十六进制转成十进制
			iArray[i] = Integer.valueOf(sArray[i], 16);
			// sb.append(sArray[i]+" ");
			sb.append(sArray[i]);
		}

		// 所有元素进行异或运算
		int result = 0;
		for (int i = 0; i < iArray.length; i++) {
			// 发现异或运算得出的结果为10进制的整型值
			result = result ^ iArray[i];
		}

		String tmp;
		// 十进制数转16进制字符串
		tmp = Integer.toHexString(result);
		if (tmp.length() == 1) {
			tmp = "0" + tmp;
		}

		System.out.println("数组" + str + "所有元素异或运算后的结果为：" + tmp);

		return sb.append(tmp).toString().toUpperCase();
	}

	public static boolean checkExtraBytes(byte[] bArray) {
		// TODO 还需校验总帧长吗？

		// 所有元素进行异或运算
		int result = 0;
		int lastIndex = bArray.length - 1;

		// 长度校验
		if (lastIndex < 7)
			return false;

		for (int i = 0; i < lastIndex; i++) {
			// 发现异或运算得出的结果为10进制的整型值
			result = result ^ bArray[i];
		}

		// System.out.println("异或运算的结果为：" + result);
		// System.out.println("bArray[lastIndex]为：" + bArray[lastIndex]);

		return result == bArray[lastIndex];
	}

	/**
	 * 获取exception中的详细信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getDetailException(Exception e) {
		StringBuffer sb = new StringBuffer();

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		Throwable cause = e.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);

		return sb.toString();
	}

	/**
	 * 得到给定字符串的后缀，如果没有后缀，返回null; 如 http://www.sina.music/f2f.mp3 则返回.mp3
	 * 
	 * @param str
	 * @return
	 */
	public static String getExtraName(String str) {
		String result = null;
		if (str.contains(".")) {
			result = str.substring(str.lastIndexOf("."));
		}
		if (result.contains("/")) {
			return null;
		}
		return result;
	}

/*	*//**
	 * 根据请求网址获取资源名称 如请求“http://mp3.tingbuwan.com/f2p.mp3 返回：f2p
	 * 
	 * @param response
	 * @return
	 *//*
	public static String getFileNameFromHttpResponse(final HttpResponse response) {
		if (response == null)
			return null;
		String result = null;
		Header header = response.getFirstHeader("Content-Disposition");
		if (header != null) {
			for (HeaderElement element : header.getElements()) {
				NameValuePair fileNamePair = element
						.getParameterByName("filename");
				if (fileNamePair != null) {
					result = fileNamePair.getValue();
					// try to get correct encoding str
					result = CharsetUtils.toCharset(result, HTTP.UTF_8,
							result.length());
					break;
				}
			}
		}
		return result;
	}*/

	/**
	 * 得到指定 型音的 带圈的标识符，比如 传递 3 则返回 ③
	 * @param tone 几型音(不能超过10，否则返回空串“”)
     * @return 带圈的音型符号，如②
     */
	public static String getToneStr(Context ctx, int tone){
		switch (tone){
			case 0:
				return ctx.getResources().getString(R.string.zero_tone);
			case 1:
				return ctx.getResources().getString(R.string.one_tone);
			case 2:
				return ctx.getResources().getString(R.string.two_tone);
			case 3:
				return ctx.getResources().getString(R.string.three_tone);
			case 4:
				return ctx.getResources().getString(R.string.four_tone);
			case 5:
				return ctx.getResources().getString(R.string.five_tone);
			case 6:
				return ctx.getResources().getString(R.string.six_tone);
			case 7:
				return ctx.getResources().getString(R.string.seven_tone);
			case 8:
				return ctx.getResources().getString(R.string.eight_tone);
			case 9:
				return ctx.getResources().getString(R.string.nine_tone);
			case 10:
				return ctx.getResources().getString(R.string.ten_tone);
			default:
				return "";
		}
	}

	/**
	 * 将给定的字符串中的竖线“|”替换成换行“\n”后返回;
	 * @param origLine 可能带有竖线的字符串
	 * @return 字符串中如果有竖线，则替换成换行符，以便在TextView中可现实换行。如无，则返回原字符串。如果origLine为空，则返回null
     */
	public static String getNoVertiLineStr(String origLine){
		if(isEmpty(origLine)){
			return null;
		}
		String result = origLine;
		if(origLine.contains("|")){//中英文字符竖线
			result = origLine.replace("|", "\n");//将文本中的“|”变成换行
		}
		if(result.contains("｜")){//日文下字符
			result = origLine.replace("｜", "\n");//将文本中的“|”变成换行
		}
		return result;
	}
	/**
	 * 将给定的字符串中的竖线“|”替换成html格式中的换行“<br/>”后返回;
	 * @param origLine 可能带有竖线的字符串
	 * @return 字符串中如果有竖线，则替换成换行符，以便在TextView中可现实换行。如无，则返回原字符串。如果origLine为空，则返回null
	 */
	public static String getNoVertiLineStrHtml(String origLine){
		if(isEmpty(origLine)){
			return null;
		}
		String result = origLine;
		if(origLine.contains("|")){//中文字符
			result = origLine.replace("|", "<br/>");//将文本中的“|”变成换行
		}
		if(origLine.contains("｜")){//日文下字符
			result = origLine.replace("｜", "<br/>");//将文本中的“|”变成换行
		}
		return result;
	}
	static String regEx = "[\u4e00-\u9fa5]";
	static Pattern pat = Pattern.compile(regEx);

	/**
	 * 判断字符串中是否含有中文
	 * @param str
	 * @return true 有；false:无
     */
	public static boolean isContainsChinese(String str)
	{
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
		{
			flg = true;
		}
		return flg;
	}
}
