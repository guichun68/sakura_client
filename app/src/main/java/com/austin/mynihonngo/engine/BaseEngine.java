package com.austin.mynihonngo.engine;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.austin.mynihonngo.utils.StringUtil;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.util.HashMap;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.nohttp.CallServer;
import com.austin.mynihonngo.engine.nohttp.FastJsonRequest;
import com.austin.mynihonngo.engine.nohttp.HttpListener;
import com.austin.mynihonngo.engine.nohttp.JsonArrayRequest;
import com.austin.mynihonngo.utils.NetworkUtil;
import com.austin.mynihonngo.utils.UIUtil;

/**
 * 服务器交互抽象类
 * 
 * @param <T>
 *            Gson要转换成的bean的类
 */
public abstract class BaseEngine<T> {
	protected static final String TAG = BaseEngine.class.getSimpleName();
	private static final int TIMEOUT = 10 * 1000;

	public void getServiceDataUsePostFastjson(HashMap<String,String> params, String url,
									   final OnResultListener listener, final boolean flag,
									   final Class<T> clazz,int what) {
		if (!NetworkUtil.checkNetwork(UIUtil.getContext())) {
			// PromptUtil.showNoNetWork(GlobalParams.MAIN);
			UIUtil.showToastSafe(UIUtil.getContext(),R.string.hintCheckNet);
		} else {
			Request<JSONObject> request = new FastJsonRequest(url, RequestMethod.POST);
			if(params != null)
			{
				request.add(params);
			}
			CallServer.getRequestInstance().add(GlobalParams.MAIN, what, request, new HttpListener<JSONObject>() {


				@Override
				public void onSucceed(int what, Response<JSONObject> response) {
					JSONObject jsonObj = response.get();
					String result = jsonObj.toString();
//					String jsonString = jsonObj.toJSONString();
					if (result.length() > 0) {
						listener.onGetData(result, what);
					}
				}

				@Override
				public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
					listener.onFailure(exception.getMessage(),what);
				}
			}, false, flag);
		}
	}

	public void getServiceDataUsePostFastjsonArray(HashMap<String,String> params, String url,
												   final OnResultListener listener, final boolean flag,
												   final Class<T> clazz,int what) {
		if (!NetworkUtil.checkNetwork(MainActivity.getForegroundActivity())) {
			// PromptUtil.showNoNetWork(GlobalParams.MAIN);
			UIUtil.showToastSafe(UIUtil.getContext(),R.string.hintCheckNet);
			if(listener!=null){
				listener.onFailure(UIUtil.getString(R.string.hintCheckNet),500);
			}
		} else {

			Request<JSONArray> request = new JsonArrayRequest(url, RequestMethod.POST);
			if(params != null)
			{
				request.add(params);
			}
			CallServer.getRequestInstance().add(GlobalParams.MAIN, what, request, new HttpListener<JSONArray>() {


				@Override
				public void onSucceed(int what, Response<JSONArray> response) {
					JSONArray jsonObj = response.get();
					if(jsonObj == null){
						if(listener != null){
							listener.onFailure("获取数据失败",404);
						}
						return;
					}
					String result = jsonObj.toString();
//					String jsonString = jsonObj.toJSONString();
					if (result.length() > 0 && listener !=null) {
						listener.onGetData(result, what);
					}
				}

				@Override
				public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
					listener.onFailure(StringUtil.isEmpty(exception.getMessage())?"未知错误":exception.getMessage(),what);
				}
			}, false, flag);
		}
	}
}
