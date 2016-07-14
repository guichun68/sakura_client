package com.austin.mynihonngo.model.impl;

import java.util.HashMap;

import com.austin.mynihonngo.engine.BaseEngine;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.model.IModel;

/**
 * Created by com.austin on 2016/6/29.
 * Desc:
 */
public class Model extends BaseEngine implements IModel {

    @Override
    public void getWordData(String url, HashMap param, OnResultListener listener,int what) {
        getServiceDataUsePostFastjsonArray(param,url,listener,false,null,what);
    }

    @Override
    public void getSakuraBunnpo(String url,HashMap<String, String> param, OnResultListener onResultListener) {
        getServiceDataUsePostFastjsonArray(param,url,onResultListener,false,null,0);
    }
}
