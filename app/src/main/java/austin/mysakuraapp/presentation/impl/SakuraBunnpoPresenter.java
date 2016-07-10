package austin.mysakuraapp.presentation.impl;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.engine.OnResultListener;
import austin.mysakuraapp.model.IWordModel;
import austin.mysakuraapp.model.bean.SakuraResult;
import austin.mysakuraapp.presentation.ISakuraBunnpoPresenter;
import austin.mysakuraapp.utils.BeanFactoryUtil;
import austin.mysakuraapp.utils.StringUtil;
import austin.mysakuraapp.utils.UIUtil;
import austin.mysakuraapp.viewfeature.IView;

/**
 * Created by austin on 2016/7/10.
 * Desc:樱花语法presnter 实现类
 */
public class SakuraBunnpoPresenter implements ISakuraBunnpoPresenter{

    String TAG = SakuraBunnpoPresenter.class.getSimpleName();
    IWordModel model;
    IView view;
    /**
     * 适配器所需数据
     */
    private List<SakuraResult> mSakuraList = new ArrayList<SakuraResult>();


    @Override
    public void init(IView view) {
        this.view = view;
        model = BeanFactoryUtil.getImpl(IWordModel.class);
    }

    @Override
    public List<SakuraResult> getSkrAdapterData() {
        return mSakuraList;
    }

    @Override
    public void getClassifyItemData(int level, int unit, final OnResultListener onResultListener) {
        if (view == null) {
            throw new NullPointerException("whether you forget to call init() method to init NounWordView in its view(activity or fragment)?");
        }
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("level", level+"");
        param.put("classNo", unit+"");
        model.getSakuraBunnpo(GlobalParams.URL_SAKURA_CLASS_ITEM, param, new OnResultListener() {
            @Override
            public void onGetData(Object obj, int what) {
                UIUtil.showTestLog(TAG,obj.toString());
                view.dismisProgress();
                if(StringUtil.isEmpty(obj.toString())){
                    UIUtil.showToastSafe(UIUtil.getString(R.string.no_enough_data));
                    return;
                }
                if((obj.toString().contains("html")) || obj.toString().contains("HTML")){
                    UIUtil.showToastSafe(R.string.hintCheckNet);
                    return;
                }
                List<SakuraResult> sakuraResults = JSON.parseArray(obj.toString(), SakuraResult.class);

                if(sakuraResults==null || sakuraResults.isEmpty() || sakuraResults.get(0) == null){
                    UIUtil.showToastSafe(UIUtil.getString(R.string.no_enough_data));
                    return;
                }

                if(sakuraResults.size()>0){
                    mSakuraList.clear();
                    mSakuraList.addAll(sakuraResults);
                    if(onResultListener != null){
                        onResultListener.onGetData(sakuraResults,200);
                    }
                }else {
                    if(onResultListener != null){
                        onResultListener.onFailure("没有更多内容了",200);
                    }
                }
            }

            @Override
            public void onFailure(String msg, int what) {
                UIUtil.showTestLog(TAG,msg);
            }
        });
    }
}
