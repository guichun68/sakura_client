package austin.mysakuraapp;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import austin.mysakuraapp.adapters.MyViewPagerAdapter;
import austin.mysakuraapp.fragments.SetingFrag;
import austin.mysakuraapp.fragments.SkrBunnpoFrag;
import austin.mysakuraapp.fragments.SkrTanngoFrag;
import austin.mysakuraapp.fragments.wordcenter.TanngoFragOfNoun;
import austin.mysakuraapp.viewfeature.IMainView;
import austin.mysakuraapp.views.lazyviewpager.LazyViewPager;

public class MainActivity extends BaseActivity implements View.OnClickListener,IMainView{
    private static MainActivity mForegroundActivity;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private LazyViewPager mViewPager;

    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    private MyViewPagerAdapter mViewPagerAdapter;
    private TextView tvTitleWordCenter,tvTitleSkrBunnpo,tvTitleSkrTanngo,tvTitleSeting;

    String defaultColor = "#CDD1D3",pressColor="#FFFFFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mForegroundActivity = this;
        bindView();

        initData();
        configViews();
    }

    private void configViews(){

        tvTitleWordCenter.setTextColor(Color.parseColor(pressColor));
        // 设置显示Toolbar
        setSupportActionBar(mToolbar);
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);

        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitlePressIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvTitleWordCenter.setOnClickListener(this);
        tvTitleSeting.setOnClickListener(this);
        tvTitleSkrBunnpo.setOnClickListener(this);
        tvTitleSkrTanngo.setOnClickListener(this);
    }

    /**
     * 渲染标题字体颜色
     * @param i 当前选中的tab角标
     */
    private void setTitlePressIndex(int i) {

        tvTitleWordCenter.setTextColor(Color.parseColor(defaultColor));
        tvTitleSeting.setTextColor(Color.parseColor(defaultColor));
        tvTitleSkrTanngo.setTextColor(Color.parseColor(defaultColor));
        tvTitleSkrBunnpo.setTextColor(Color.parseColor(defaultColor));
        switch (i){
            case 0:
                tvTitleWordCenter.setTextColor(Color.parseColor(pressColor));
                break;
            case 1:
                tvTitleSkrBunnpo.setTextColor(Color.parseColor(pressColor));
                break;
            case 2:
                tvTitleSkrTanngo.setTextColor(Color.parseColor(pressColor));
                break;
            case 3:
                tvTitleSeting.setTextColor(Color.parseColor(pressColor));
                break;
        }
    }

    private void initData() {
        // Tab的标题采用string-array的方法保存，在res/values/arrays.xml中写
        mTitles = getResources().getStringArray(R.array.tab_titles);
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        TanngoFragOfNoun tanngoFragOfNoun = new TanngoFragOfNoun();
        SkrBunnpoFrag skrBunnpo = new SkrBunnpoFrag();
        SkrTanngoFrag skrTango = new SkrTanngoFrag();
        SetingFrag setFrag = new SetingFrag();

        mFragments.add(tanngoFragOfNoun);
        mFragments.add(skrBunnpo);
        mFragments.add(skrTango);
        mFragments.add(setFrag);
    }

    @Override
    void bindView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mViewPager = (LazyViewPager) findViewById(R.id.id_viewpager);
        //tvTitleWordCenter,tvTitleSkrBunnpo,tvTitleSkrTanngo,tvTitleSeting;
        tvTitleWordCenter = (TextView) findViewById(R.id.tv_title_word);
        tvTitleSkrBunnpo = (TextView) findViewById(R.id.tv_title_skr_bunnpo);
        tvTitleSkrTanngo = (TextView) findViewById(R.id.tv_title_skr_tanngo);
        tvTitleSeting = (TextView) findViewById(R.id.tv_title_seting);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_word:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_title_skr_bunnpo:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tv_title_skr_tanngo:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tv_title_seting:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
    public static MainActivity getForegroundActivity() {
        return mForegroundActivity;
    }
    @Override
    public void showToolBar() {
        ObjectAnimator ani = ObjectAnimator.ofFloat(mToolbar,View.TRANSLATION_Y,-mToolbar.getHeight(),0);
        ani.setDuration(500);
        ani.start();
    }

    @Override
    public void hideToolBar() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mToolbar,View.TRANSLATION_Y,0,-mToolbar.getHeight());
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public boolean isToobarShow() {
        return mToolbar.isShown();
    }
}
