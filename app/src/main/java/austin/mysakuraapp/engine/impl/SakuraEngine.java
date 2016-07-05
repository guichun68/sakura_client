package austin.mysakuraapp.engine.impl;


import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.engine.BaseEngine;
import austin.mysakuraapp.engine.ISakuraEngine;
import austin.mysakuraapp.engine.OnResultListener;

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
