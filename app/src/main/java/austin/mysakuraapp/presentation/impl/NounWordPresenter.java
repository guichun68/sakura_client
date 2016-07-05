package austin.mysakuraapp.presentation.impl;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import austin.mysakuraapp.R;
import austin.mysakuraapp.engine.OnResultListener;
import austin.mysakuraapp.model.IWordModel;
import austin.mysakuraapp.model.bean.WordResult;
import austin.mysakuraapp.presentation.INounWordPresenter;
import austin.mysakuraapp.utils.BeanFactoryUtil;
import austin.mysakuraapp.utils.BusiUtils;
import austin.mysakuraapp.utils.StringUtil;
import austin.mysakuraapp.utils.UIUtil;
import austin.mysakuraapp.viewfeature.INounWordView;

/**
 * Created by austin on 2016/6/29.
 * Desc: 名词页面行为接口实现
 */
public class NounWordPresenter implements INounWordPresenter {

    String TAG = NounWordPresenter.class.getSimpleName();
    IWordModel model;
    INounWordView view;
    private List<WordResult> wordsTemp;//当前列表所有数据（已经刷新出来的），点击"上一个"、"下一个"时从中取值。
    /**
     * 适配器所需数据
     */
    private List<WordResult> mWordsList = new ArrayList<WordResult>();

    @Override
    public void init(INounWordView view) {
        this.view = view;
        model = BeanFactoryUtil.getImpl(IWordModel.class);
    }

    @Override
    public void getWordItemData(Integer classifyType, Integer level, Integer pageNo, boolean isRefresh) {
        if (view == null) {
            throw new NullPointerException("whether you forget to call init() method to init NounWordView in its view(activity or fragment)?");
        }
        HashMap<String, String> param = new HashMap<String, String>();
        if (isRefresh) {
            if (wordsTemp != null) {
                wordsTemp.clear();
            }
//            view.setPageNo(1);
        }
        String url = BusiUtils.parseUrlByClassfi(classifyType);
        param.put("classifyType", classifyType + "");
        param.put("pageNo", pageNo + "");
        if (level != null && level != 0) {
            param.put("level", level + "");
        }
        model.getWordData(url, param, new GetWordDataListener(isRefresh));
    }

    class GetWordDataListener implements OnResultListener {

        private boolean isRefresh;

        GetWordDataListener(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        public void onGetData(Object obj, int what) {
            Log.e(TAG, "获取数据成功");
            processData(obj.toString(), isRefresh);
        }

        @Override
        public void onFailure(String msg) {
            Log.e(TAG, "获取数据失败");
            view.dismisProgress();
        }

        private void processData(String result, boolean isRefresh) {
            if (StringUtil.isEmpty(result)) {
                UIUtil.showToastSafe(R.string.get_data_error);
                Log.e(TAG, "occurred an error.parseResponse() method in JsonArrayRequest.java may return null. ");
                return;
            }
            List<WordResult> responseList;
            try {
                responseList = JSONArray.parseArray(result, WordResult.class);
            } catch (Exception e) {
                JSONObject parseObject = JSON.parseObject(result);
                String str = (String) parseObject.get("error");
                if (!StringUtil.isEmpty(str)) {
                    UIUtil.showToastSafe("发生错误，error_code=" + str);
                } else {
                    UIUtil.showToastSafe(R.string.get_data_error);
                }
                e.printStackTrace();
                return;
            }
            if (StringUtil.isEmpty(result) || responseList.size() == 0) {
                view.onGetItemData(null,200);
                return;
            }
            List<WordResult> words = responseList;
//		SharedPreferencesUtils.putString("words", JSONArray.toJSONString(words));
            if (words == null || words.isEmpty() || words.get(0) == null) {
                view.onGetItemData(null, 0);
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
                view.onGetItemData(result,200);
            }
        }

    }

    @Override
    public List<WordResult> getAdapterData(){
         return mWordsList;
    }


}
