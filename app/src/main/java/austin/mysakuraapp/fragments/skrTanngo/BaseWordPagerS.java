package austin.mysakuraapp.fragments.skrTanngo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ArgumentKey;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.GlobalParams;

/**
 * Created by austin on 2016/6/28.
 * Desc: 单词中心之形容词Fragment
 */
public class BaseWordPagerS extends Fragment {

    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<BaseWordPagerInnerS> pagers = new ArrayList<>();
    TangoFragVPAdapterS adapter;

    boolean isshow ;//当前TabLayout的显示状态，默认显示。
    int disy;//一次滑动的距离
    //顶部tab导航栏标题集合
    private String[] titles;
    private int wordType;//要实例化的单词页面类型（如名词？形容词？）,从ConstantValue的wordTypeNoun等取值

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_wordcenter,container,false);
        titles = getArguments().getStringArray(ArgumentKey.TitleArguBundle);
        wordType = getArguments().getInt(ConstantValue.WordCenterType);
        bindView();
        initData();
        configView();
        isshow = isTablayoutShowing();
        return view;
    }

    public boolean isTablayoutShowing(){
        return mTabLayout.isShown();
    }

    private void bindView() {
        mTabLayout = (TabLayout) view.findViewById(R.id.tl_tablayout);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_tabvp);
    }
    private void initData() {
        initSpecifyTypeViews();
        getPagers().get(0).initBaseData();
    }
    public String[] getTabLayoutTitles() {
        return titles;
    }

    private void configView() {
        adapter = new TangoFragVPAdapterS(getPagers(),getTabLayoutTitles());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                getPagers().get(position).initBaseData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                GlobalParams.TAB_LAYOUT_HEIGHT = mTabLayout.getHeight();
                Log.e("tabLayoutH",GlobalParams.TAB_LAYOUT_HEIGHT+"");
                Log.e("tabLayoutHM:",mTabLayout.getMeasuredHeight()+"");
                if(Build.VERSION.SDK_INT <Build.VERSION_CODES.JELLY_BEAN){
                    mTabLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }else{
                    mTabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public List<BaseWordPagerInnerS> getPagers() {
        if(pagers != null && pagers.size()>0)
            return pagers;
        initSpecifyTypeViews();
        return pagers;
    }

    public void hideTabLayout() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTabLayout,View.TRANSLATION_Y,0,-mTabLayout.getHeight());
        animator.setDuration(500);
        animator.start();
    }

    public void showTabLayout() {
        ObjectAnimator ani = ObjectAnimator.ofFloat(mTabLayout,View.TRANSLATION_Y,-mTabLayout.getHeight(),0);
        ani.setDuration(500);
        ani.start();
    }

    public void initSpecifyTypeViews() {
        pagers.clear();
        BaseWordPagerInnerS pagerType1=null,pagerType2=null,pagerType3=null,pagerType4=null;
        switch (wordType){
            case ConstantValue.WordTypeNoun:
                pagerType1 = new TangoFragBasePagerInner(getActivity(), ConstantValue.NOUN_TYPE_ANIMAL, null);
                pagerType2 = new TangoFragBasePagerInner(getActivity(), ConstantValue.NOUN_TYPE_PLANT, null);
                pagerType3 = new TangoFragBasePagerInner(getActivity(), ConstantValue.NOUN_TYPE_VEHICLE, null);
                pagerType4 = new TangoFragBasePagerInner(getActivity(), ConstantValue.NOUN_TYPE_OTHER, null);
            break;
            case ConstantValue.WordTypeVerb:
                pagerType1 = new TangoFragBasePagerInner(getActivity(), ConstantValue.VERB_TYPE_ONE, null);
                pagerType2 = new TangoFragBasePagerInner(getActivity(), ConstantValue.VERB_TYPE_TWO, null);
                pagerType3 = new TangoFragBasePagerInner(getActivity(), ConstantValue.VERB_TYPE_THREE, null);
            break;
            case ConstantValue.WordTypeAdj:
                pagerType1 = new TangoFragBasePagerInner(getActivity(), ConstantValue.ADJ_TYPE_ONE, null);
                pagerType2 = new TangoFragBasePagerInner(getActivity(), ConstantValue.ADJ_TYPE_TWO, null);
            break;
            case ConstantValue.WordTypeOther:
                pagerType1 = new TangoFragBasePagerInner(getActivity(), ConstantValue.OTHER_TYPE_ONE, null);
                pagerType2 = new TangoFragBasePagerInner(getActivity(), ConstantValue.OTHER_TYPE_TWO, null);
                pagerType3 = new TangoFragBasePagerInner(getActivity(), ConstantValue.OTHER_TYPE_ALL, null);
            break;
        }
        if(pagerType1 != null){pagers.add(pagerType1); pagerType1.setParentFrag(this);}
        if(pagerType2 != null)pagers.add(pagerType2);
        if(pagerType3 != null)pagers.add(pagerType3);
        if(pagerType4 != null)pagers.add(pagerType4);
    }

    class TangoFragBasePagerInner extends BaseWordPagerInnerS {

        public TangoFragBasePagerInner(Context context, int classItemId, Integer level) {
            super(context, classItemId, level);
        }
        @Override
        public void ctrlToolBarShowOrHide(RecyclerView recyclerView, int dx, int dy) {
            //得到第一个item
            int firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if(firstVisibleItem == 0){//当前第一个可见item是否是第一个，如是则显示toolbar
                if(!isshow){//如果此时toolbar未显示，则显示
                    isshow = true;
//                    ((IMainView)getActivity()).showToolBar();
                    showTabLayout();
                }
            }else{//不是第一个
                if(disy>100 && isshow){//滑动距离大于100且toolbar显示中，继续向下滚，隐藏
                    isshow = false;
//                    ((IMainView)getActivity()).hideToolBar();
                    hideTabLayout();
                    disy = 0;
                }
                if(disy<-100 && !isshow){//向上滑动且距离大于100且toolbar隐藏中，则显示
                    isshow = true;
//                    ((IMainView)getActivity()).showToolBar();
                    showTabLayout();
                    disy = 0;
                }
            }
            if((isshow && dy>0)||(!isshow && dy <0)){//增加滑动的距离，只有再出发两种状态的时候才进行叠加
                disy += dy;
            }
        }
    }

}