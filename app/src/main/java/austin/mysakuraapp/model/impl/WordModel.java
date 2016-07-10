package austin.mysakuraapp.model.impl;

import java.util.HashMap;

import austin.mysakuraapp.engine.BaseEngine;
import austin.mysakuraapp.engine.OnResultListener;
import austin.mysakuraapp.model.IWordModel;
import austin.mysakuraapp.presentation.INounWordPresenter;

/**
 * Created by austin on 2016/6/29.
 * Desc:
 */
public class WordModel extends BaseEngine implements IWordModel {

    @Override
    public void getWordData(String url, HashMap param, OnResultListener listener,int what) {
        getServiceDataUsePostFastjsonArray(param,url,listener,false,null,what);
    }

    @Override
    public void getSakuraBunnpo(String url,HashMap<String, String> param, OnResultListener onResultListener) {
        getServiceDataUsePostFastjsonArray(param,url,onResultListener,false,null,0);
    }
}
