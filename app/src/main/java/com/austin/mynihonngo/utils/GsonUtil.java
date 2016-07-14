package com.austin.mynihonngo.utils;

import com.google.gson.Gson;


public class GsonUtil {
	public static <T> T jsonToBean(String result,Class<T> clazz){
		Gson gson = new Gson();
		return gson.fromJson(result, clazz);
	}
}
