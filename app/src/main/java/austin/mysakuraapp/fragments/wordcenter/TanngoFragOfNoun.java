package austin.mysakuraapp.fragments.wordcenter;

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
import austin.mysakuraapp.adapters.TangoFragVPAdapter;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.viewfeature.IMainView;

/**
 * Created by austin on 2016/6/28.
 * Desc: 单词中心之名词Fragment
 */
public class TanngoFragOfNoun extends Fragment {

    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<BaseWordPager> pagers = new ArrayList<>();
    TangoFragVPAdapter adapter;

    boolean isshow ;//当前toolbar的显示状态，默认显示。
    int disy;//一次滑动的距离
    private String[] titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_wordcenter,container,false);
        bindView();
        initData();
        configView();
        isshow = ((IMainView)getActivity()).isToobarShow();
        return view;
    }
    private void bindView() {
        mTabLayout = (TabLayout) view.findViewById(R.id.tl_tablayout);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_tabvp);

        titles = getActivity().getResources().getStringArray(R.array.noun_tab_title);
    }
    private void initData() {
        initSpecifyTypeViews();

        adapter = new TangoFragVPAdapter(getPagers(),getTabLayoutTitles());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getPagers().get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getPagers().get(0).initData();
    }
    public String[] getTabLayoutTitles() {
        return titles;
    }

    private void configView() {
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

    public List<BaseWordPager> getPagers() {
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
        BaseWordPager pagerType1,pagerType2,pagerType3,pagerType4;
        pagerType1 = new TangoFragBasePager(getActivity(), ConstantValue.NOUN_TYPE_ANIMAL, null);
        pagerType2 = new TangoFragBasePager(getActivity(), ConstantValue.NOUN_TYPE_PLANT, null);
        pagerType3 = new TangoFragBasePager(getActivity(), ConstantValue.NOUN_TYPE_VEHICLE, null);
        pagerType4 = new TangoFragBasePager(getActivity(), ConstantValue.NOUN_TYPE_OTHER, null);
        pagers.add(pagerType1);
        pagers.add(pagerType2);
        pagers.add(pagerType3);
        pagers.add(pagerType4);
    }

    class TangoFragBasePager extends BaseWordPager{

        public TangoFragBasePager(Context context, int classItemId, Integer level) {
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