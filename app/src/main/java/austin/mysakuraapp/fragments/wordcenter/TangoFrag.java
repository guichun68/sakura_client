package austin.mysakuraapp.fragments.wordcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import austin.mysakuraapp.R;
import austin.mysakuraapp.adapters.TangoFragVPAdapter;
import austin.mysakuraapp.model.TanngoType;
import austin.mysakuraapp.presentation.ITangoFragPresenter;
import austin.mysakuraapp.utils.BeanFactoryUtil;
import austin.mysakuraapp.viewfeature.ITangoFragView;

/**
 * Created by austin on 2016/6/28.
 * Desc: 单词中心
 */
public class TangoFrag extends Fragment implements ITangoFragView{
    private ITangoFragPresenter presenter;

    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //左侧侧拉栏目关联指定类的一个集合
//    private List<BaseWordPager> pagers = new ArrayList<BaseWordPager>();
    // ViewPager的数据适配器
//    private TangoPagerAdapter mViewPagerAdapter;
    TangoFragVPAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_wordcenter,container,false);
        presenter = BeanFactoryUtil.getImpl(ITangoFragPresenter.class);
        if(presenter==null){
            throw new NullPointerException("check if you forget config ITangoFragPresenter in bean property file.");
        }
        presenter.onCreate(getActivity());
        bindView();
        initData();
        configView();
        return view;
    }
    private void bindView() {
        mTabLayout = (TabLayout) view.findViewById(R.id.tl_tablayout);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_tabvp);
    }
    private void initData() {
        presenter.initSpecifyTypeViews(TanngoType.Noun);

        adapter = new TangoFragVPAdapter(presenter.getPagers(),presenter.getTabLayoutTitles());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                presenter.getPagers().get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        presenter.getPagers().get(0).initData();
    }

    private void configView() {
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }


}