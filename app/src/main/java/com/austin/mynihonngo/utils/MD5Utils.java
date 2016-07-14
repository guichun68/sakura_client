package com.austin.mynihonngo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	/** 加密为32位md5,如加密失败则返回原值
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		if(value == null){
			return null;
		}
		try {
			//1 获得摘要算法对象
			MessageDigest messageDigest  = MessageDigest.getInstance("MD5");
			//2加密
			byte[] md5ValueByteArray = messageDigest.digest(value.getBytes());
			//3 将加密后的结果从10转16进制
			BigInteger bigInteger = new BigInteger(1, md5ValueByteArray);
			//4 以16进行返回
			return bigInteger.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return value; //加密失败，不再加密，返回原值
		
	}
	
	/**
	 * 获取某个文件的md5值
	 * @param path 文件的绝对路径
	 */
	public static String getFileMd5(String path){
		try {
			//计算课程表的md5数字摘要。
			MessageDigest digest = MessageDigest.getInstance("md5");
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))!=-1){
				digest.update(buffer, 0, len);
			}
			byte[]  result = digest.digest();
			//把result的内容 打印出来 16进制
			StringBuilder sb = new StringBuilder();
			for(byte b : result){
				int number = b & 0xff ;//- 2;//加盐
				String str = Integer.toHexString(number);
				if(str.length()==1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
