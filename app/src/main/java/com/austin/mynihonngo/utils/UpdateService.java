package com.austin.mynihonngo.utils;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.IBinder;

import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.Response;

import com.austin.mynihonngo.comm.ConstantValue;
import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.IAppEngine;
import com.austin.mynihonngo.engine.nohttp.HttpListener;

public class UpdateService extends Service implements HttpListener<JSONObject> {

	private PackageManager pm;
	private int currVersionCode;
	private CheckUpdateCallBack callBack;


	@Override
	public void onCreate() {
		
		pm = getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(getPackageName(), 0);
			currVersionCode = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}
	
	public class MyBinder extends Binder{
		public void callCheckUpdate(CheckUpdateCallBack callBack){
			checkUpdate(callBack);

		}
	}
	void checkUpdate(CheckUpdateCallBack callBack){
		this.callBack = callBack;
		IAppEngine engine = BeanFactoryUtil.getImpl(IAppEngine.class);
		engine.checkUpdate(currVersionCode,UpdateService.this, false);
	}
	
	@Override
	public void onSucceed(int what, Response<JSONObject> response) {
		switch (what) {
		case ConstantValue.WHAT_UPDATE:
//			String respone = response.toString();
			JSONObject jsonObj = response.get();
			String code = jsonObj.getString("code");
			GlobalParams.isCheckedUpdate = true;
			// 415不需要更新 ; 416有更新\
			if ("400".equals(code)) {
				String url = (String) jsonObj.get("url");// 下载地址
				String descrip = (String)(jsonObj.get("des"));
				String isForceUpdate = (String) jsonObj.get("forceUpdate");
//				Boolean isForceUpdate = jsonObj.getBoolean("forceUpdate");
				if(isForceUpdate != null && isForceUpdate.equals("YES")){//需要强制更新，不更新则退出
					Intent inten = new Intent(UpdateService.this,ForceUpdateActivity.class);
					inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					inten.putExtra("desc", descrip);
					inten.putExtra("url", url);
					startActivity(inten);
				}else{
					//TODO 启动一个透明背景的Activty，弹出下载新版本提示dialog
					Intent intent = new Intent(UpdateService.this,UpdateActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("desc", descrip);
					intent.putExtra("url", url);
					startActivity(intent);
				}
				
//				String description = new String( (String)(jsonObj.get("description")),"UTF-8");// 版本描述
//				showConfirmUpdateDialog(url, descrip);
				
			} else {
				if ("415".equals(code)) {
					if(callBack!=null){
						callBack.shouldUpdate(false);
					}
//					OtherUtils.showShortToastInAnyThread(act, "已是最新版本.");
				} else {
					//OtherUtils.showShortToastInAnyThread(act, "未知参数，返回代码:" + code);
				}
			}
			
			break;
		case ConstantValue.FAILURE:
			/*Intent intent = new Intent(UpdateService.this,UpdateActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("desc", "好");
			intent.putExtra("url", "http://www.baidu.com");
			startActivity(intent);
//			OtherUtils.showShortToastInAnyThread(UpdateService.this, R.string.network_error);
//			OtherUtils.showShortToast(UpdateService.this, "网络异常,检查更新未成功");
			OtherUtils.logTestInfo("UpdateService", "网络异常，检查更新未成功");
			Constants.isCheckedUpdate = false;*/
			break;
		default:
			break;
		}
		
	}


	@Override
	public void onFailed(int what, String url, Object tag, Exception exception,
			int responseCode, long networkMillis) {
		// TODO Auto-generated method stub
		
	}
	public interface CheckUpdateCallBack{
		void shouldUpdate(boolean shoudUpdate);
	}
	
}
