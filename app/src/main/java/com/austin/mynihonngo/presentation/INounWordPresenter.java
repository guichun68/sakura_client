package com.austin.mynihonngo.presentation;

import java.util.List;

import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.model.bean.WordResult;
import com.austin.mynihonngo.viewfeature.IView;

/**
 * Created by com.austin on 2016/6/29.
 * Desc: 名词(单词中心和樱花单词)页面和语法页面行为接口
 */
public interface INounWordPresenter {

    void init(IView view);

    /**
     * 获取单词
     * @param classifyType 对应的侧边栏大类
     * @param level 樱日单词页，代表要获取单词所在的级别，为null，表示非樱日单词
     * @param pageNo 页数
     * @param isRefresh 是否是刷新操作
     */
    void getWordItemData(Integer classifyType, Integer level, Integer pageNo, boolean isRefresh, OnResultListener listener);

    /**
     * 获取填充RecyclerView的数据
     * @return
     */
    List<WordResult> getAdapterData();

}
