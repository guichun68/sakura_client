package com.austin.mynihonngo.engine.impl;


import java.util.HashMap;

import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.BaseEngine;
import com.austin.mynihonngo.engine.IWordCenterEngine;
import com.austin.mynihonngo.engine.OnResultListener;

public class WordCenterEngineImpl extends BaseEngine implements
		IWordCenterEngine {

	@Override
	public void getWordCenterData(OnResultListener listener, Boolean flag,int what) {
		String url = GlobalParams.URL_SLIDING_MENU_WORD_CENTER;
//		getServiceData(null, url, listener, flag);
		getServiceDataUsePostFastjson(null,url,listener,flag,null,what);
	}

	@Override
	public void getClassifyItemData(String url,HashMap<String,String> param,
			OnResultListener onResultListener, boolean flag,int what) {
//		getServiceData(param, url, onResultListener, flag);
		getServiceDataUsePostFastjsonArray(param,url,onResultListener,flag,null,what);
	}
}
