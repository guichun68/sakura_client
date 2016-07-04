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
    public void getWordData(String url, HashMap param, OnResultListener listener) {
        getServiceDataUsePostFastjsonArray(param,url,listener,false,null);
    }
}
