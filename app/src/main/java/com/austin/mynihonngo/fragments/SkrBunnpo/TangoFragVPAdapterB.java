package com.austin.mynihonngo.fragments.SkrBunnpo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by com.austin on 2016/7/1.
 * Desc: 樱花单词页面的ViewPager适配器
 */
public class TangoFragVPAdapterB extends PagerAdapter {

    List<BaseWordPagerInnerB> pagers;
    String[] titls;
    public TangoFragVPAdapterB(List<BaseWordPagerInnerB> pagers, String[]titls){
        this.pagers = pagers;
        this.titls = titls;
    }

    // TabLayout关联viewpager后，其标题会自动从此方法获取
    @Override
    public CharSequence getPageTitle(int position) {
        return titls[position];
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView = pagers.get(position).getRootView();
        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagers.get(position).getRootView());
    }
}
