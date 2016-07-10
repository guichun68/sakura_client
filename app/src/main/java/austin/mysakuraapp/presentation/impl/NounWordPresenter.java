package austin.mysakuraapp.presentation.impl;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.engine.OnResultListener;
import austin.mysakuraapp.model.IWordModel;
import austin.mysakuraapp.model.bean.WordResult;
import austin.mysakuraapp.presentation.INounWordPresenter;
import austin.mysakuraapp.utils.BeanFactoryUtil;
import austin.mysakuraapp.utils.BusiUtils;
import austin.mysakuraapp.utils.StringUtil;
import austin.mysakuraapp.utils.UIUtil;
import austin.mysakuraapp.viewfeature.IView;

/**
 * Created by austin on 2016/6/29.
 * Desc: 名词页面行为接口实现
 */
public class NounWordPresenter implements INounWordPresenter {

    String TAG = NounWordPresenter.class.getSimpleName();
    IWordModel model;
    IView view;
    /**
     * 适配器所需数据
     */
    private List<WordResult> mWordsList = new ArrayList<WordResult>();

    @Override
    public void init(IView view) {
        this.view = view;
        model = BeanFactoryUtil.getImpl(IWordModel.class);
    }

    @Override
    public void getWordItemData(Integer classifyType, Integer level, Integer pageNo, boolean isRefresh,OnResultListener listener) {
        if (view == null) {
            throw new NullPointerException("whether you forget to call init() method to init NounWordView in its view(activity or fragment)?");
        }
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("classifyType", classifyType + "");
        param.put("pageNo", pageNo + "");
        String url=null;
        if (level != null && level != 0) {
            param.put("level", level + "");
            url = GlobalParams.URL_SAKURA_WORD;
        }else{
            url = BusiUtils.parseUrlByClassfi(classifyType);
        }
        model.getWordData(url, param, new GetWordDataListener(isRefresh,listener), ConstantValue.WHAT_BASE);
    }

    class GetWordDataListener implements OnResultListener {

        private boolean isRefresh;
        private OnResultListener viewListener;
        private int what;

        GetWordDataListener(boolean isRefresh,OnResultListener viewListener) {
            this.isRefresh = isRefresh;
            this.viewListener = viewListener;
        }

        @Override
        public void onGetData(Object obj, int what) {
//            Log.e(TAG, "获取数据成功");
            this.what = what;
            processData(obj.toString(), isRefresh);
        }

        @Override
        public void onFailure(String msg,int what) {
            Log.e(TAG, "获取数据失败");
            view.dismisProgress();
        }

        private void processData(String result, boolean isRefresh) {
            if (StringUtil.isEmpty(result)) {
                if(viewListener!=null){viewListener.onFailure(UIUtil.getString(R.string.get_data_error),what);}
                Log.e(TAG, "occurred an error.parseResponse() method in JsonArrayRequest.java may return null. ");
                return;
            }
            List<WordResult> responseList;
            try {
                responseList = JSONArray.parseArray(result, WordResult.class);
            } catch (Exception e) {
                if(viewListener != null){viewListener.onFailure(UIUtil.getString(R.string.get_data_error),what);}
                JSONObject parseObject = JSON.parseObject(result);
                String str = (String) parseObject.get("error");
                if (!StringUtil.isEmpty(str)) {
                    UIUtil.showTestLog(TAG,"发生错误，error_code=" + str);
                } else {
                    UIUtil.showTestLog(TAG,UIUtil.getString(R.string.get_data_error));
                }
                e.printStackTrace();
                return;
            }
            if (StringUtil.isEmpty(result) || responseList.size() == 0) {
//                view.onGetItemData(null,what);
                if(viewListener!=null) {viewListener.onGetData(null,what);}
                return;
            }
            List<WordResult> words = responseList;
//		SharedPreferencesUtils.putString("words", JSONArray.toJSONString(words));
            if (words == null || words.isEmpty() || words.get(0) == null) {
                if(viewListener!=null){viewListener.onGetData(null,what);}
                return;
            }
            if (words.size() > 0) {
                if (isRefresh) {
                    //重新获取一次
                    //先清除
                    mWordsList.clear();
                }
                mWordsList.addAll(words);
                //移除顶部和底部view的操作
                if(viewListener!=null){viewListener.onGetData(result,what);}
            }
        }

    }

    @Override
    public List<WordResult> getAdapterData(){
         return mWordsList;
    }




}
