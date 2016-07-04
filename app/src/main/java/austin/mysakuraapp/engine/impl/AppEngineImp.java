package austin.mysakuraapp.engine.impl;

import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;

import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.engine.IAppEngine;
import austin.mysakuraapp.engine.nohttp.CallServer;
import austin.mysakuraapp.engine.nohttp.FastJsonRequest;
import austin.mysakuraapp.engine.nohttp.HttpListener;
import austin.mysakuraapp.utils.UIUtil;

public class AppEngineImp implements IAppEngine {

	@Override
	public void checkUpdate(int versionCode, HttpListener<JSONObject> callback, Boolean flag) {
//		Request<JSONObject> request = NoHttp.createStringRequest(GlobalParams.URL_UPDATE, RequestMethod.POST);
		Request<JSONObject> request =  new FastJsonRequest(GlobalParams.URL_UPDATE, RequestMethod.POST);
		request.add("versionCode", versionCode);
		CallServer.getRequestInstance().add(UIUtil.getContext(), ConstantValue.WHAT_UPDATE, request, callback, false, false);
	}


}
