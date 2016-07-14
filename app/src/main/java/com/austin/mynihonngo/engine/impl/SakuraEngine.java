package com.austin.mynihonngo.engine.impl;


import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.BaseEngine;
import com.austin.mynihonngo.engine.ISakuraEngine;
import com.austin.mynihonngo.engine.OnResultListener;

public class SakuraEngine extends BaseEngine implements ISakuraEngine {

	@Override
	public void getSakuraData(OnResultListener listener, Boolean flag,int what) {
		String url = GlobalParams.URL_SLIDING_MENU_SAKURA;
		getServiceDataUsePostFastjson(null,url,listener,flag,null,what);
	}
	
	@Override
	public void getSakuraWordCenterData(OnResultListener listener, Boolean flag,int what) {
		String url = GlobalParams.URL_SAKURA_WORD_CENTER;
		getServiceDataUsePostFastjson(null,url,listener,flag,null,what);
	}
}
