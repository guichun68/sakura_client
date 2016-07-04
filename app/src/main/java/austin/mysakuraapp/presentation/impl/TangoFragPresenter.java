package austin.mysakuraapp.presentation.impl;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.fragments.wordcenter.BaseWordPager;
import austin.mysakuraapp.model.TanngoType;
import austin.mysakuraapp.presentation.ITangoFragPresenter;

/**
 * Created by austin on 2016/7/1.
 * Desc:
 */
public class TangoFragPresenter implements ITangoFragPresenter {
    //每个侧栏对应的页面（是一个多个pager的集合，填充到一个viewpager中）
    private List<BaseWordPager> pagers = new ArrayList<>();
    private Resources res;
    private String[] titles;
    private Context context;
    private TanngoType type;
    @Override
    public void onCreate(Context context) {
        this.context = context;
        res = context.getResources();
        titles = res.getStringArray(R.array.noun_tab_title);
    }

    @Override
    public String[] getTabLayoutTitles() {
        return titles;
    }

    @Override
    public List<BaseWordPager> getPagers() {
        if(pagers != null && pagers.size()>0)
            return pagers;
        initSpecifyTypeViews(type);
        return pagers;
    }

    @Override
    public void initSpecifyTypeViews(TanngoType type) {
        this.type = type;
        pagers.clear();
        BaseWordPager pagerType1 = null;
        BaseWordPager pagerType2 = null;
        BaseWordPager pagerType3 = null;
        BaseWordPager pagerType4 = null;
        switch (type){
            case Noun:
                pagerType1 = new BaseWordPager(context, ConstantValue.NOUN_TYPE_ANIMAL, null);
                pagerType2 = new BaseWordPager(context, ConstantValue.NOUN_TYPE_PLANT, null);
                pagerType3 = new BaseWordPager(context, ConstantValue.NOUN_TYPE_VEHICLE, null);
                pagerType4 = new BaseWordPager(context, ConstantValue.NOUN_TYPE_OTHER, null);
                break;
            case Verb:
                break;
            case Adj:
                break;
            case Other:
                break;
        }
        if(pagerType1!=null)pagers.add(pagerType1);
        if(pagerType2!=null)pagers.add(pagerType2);
        if(pagerType3!=null)pagers.add(pagerType3);
        if(pagerType4!=null)pagers.add(pagerType4);
    }


}
