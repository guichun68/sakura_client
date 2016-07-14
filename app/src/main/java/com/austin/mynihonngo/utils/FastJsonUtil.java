/**
 * @(#)UserAction.java	05/15/2014
 * 
 * Copyright (c) 2014 SAILSORS INSTRUMENTS LTD.,BEIJING.All rights reserved.
 * Created by 2014-05-15
 */
package com.austin.mynihonngo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastJsonUtil {

	public static String fromMap(Map<String, Object> map) {
		String result = JSON.toJSONString(map);
		return result;
	}

	public static Map<String, Object> toMap(String data) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isEmpty(data)) {
			return result;
		}
		Object jsonResult = JSON.parse(data);
		if (!StringUtil.isNotNull(jsonResult)) {
			return result;
		}
		if (jsonResult instanceof JSONObject) {
			result = (Map<String, Object>) jsonResult;
		}

		return result;
	}

	public static List<Map<String, Object>> toList(String data) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (StringUtil.isEmpty(data)) {
			return resultList;
		}
		Object jsonResult = JSON.parse(data);

		if (!StringUtil.isNotNull(jsonResult)) {
			return resultList;
		}

		if (jsonResult instanceof JSONObject) {
			Map<String, Object> item = (Map<String, Object>) jsonResult;
			resultList.add(item);
		} else if (jsonResult instanceof JSONArray) {
			resultList = (List<Map<String, Object>>) jsonResult;
		}
		return resultList;
	}

}