package austin.mysakuraapp.engine.impl;


import java.util.HashMap;

import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.engine.BaseEngine;
import austin.mysakuraapp.engine.IWordCenterEngine;
import austin.mysakuraapp.engine.OnResultListener;

public class WordCenterEngineImpl extends BaseEngine implements
		IWordCenterEngine {

	@Override
	public void getWordCenterData(OnResultListener listener, Boolean flag) {
		String url = GlobalParams.URL_SLIDING_MENU_WORD_CENTER;
//		getServiceData(null, url, listener, flag);
		getServiceDataUsePostFastjson(null,url,listener,flag,null);
	}

	@Override
	public void getClassifyItemData(String url,HashMap<String,String> param,
			OnResultListener onResultListener, boolean flag) {
//		getServiceData(param, url, onResultListener, flag);
		getServiceDataUsePostFastjsonArray(param,url,onResultListener,flag,null);
	}
}
