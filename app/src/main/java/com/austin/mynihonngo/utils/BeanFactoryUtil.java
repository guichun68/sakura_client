package com.austin.mynihonngo.utils;

import java.io.IOException;
import java.util.Properties;


/**
 * 工厂
 * @author Administrator
 */
public class BeanFactoryUtil {
	public static Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(BeanFactoryUtil.class.getClassLoader().getResourceAsStream("bean.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定的实例
	 * @return
	 */
	public static <T> T getImpl(Class<T> clazz) {
		String key = clazz.getSimpleName();
		String className = properties.getProperty(key);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
