package com.austin.mynihonngo.engine.impl;

import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;

import com.austin.mynihonngo.comm.ConstantValue;
import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.IAppEngine;
import com.austin.mynihonngo.engine.nohttp.CallServer;
import com.austin.mynihonngo.engine.nohttp.FastJsonRequest;
import com.austin.mynihonngo.engine.nohttp.HttpListener;
import com.austin.mynihonngo.utils.UIUtil;

public class AppEngineImp implements IAppEngine {

	@Override
	public void checkUpdate(int versionCode, HttpListener<JSONObject> callback, Boolean flag) {
//		Request<JSONObject> request = NoHttp.createStringRequest(GlobalParams.URL_UPDATE, RequestMethod.POST);
		Request<JSONObject> request =  new FastJsonRequest(GlobalParams.URL_UPDATE, RequestMethod.POST);
		request.add("versionCode", versionCode);
		CallServer.getRequestInstance().add(UIUtil.getContext(), ConstantValue.WHAT_UPDATE, request, callback, false, false);
	}


}
