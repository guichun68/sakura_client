package austin.mysakuraapp;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import austin.mysakuraapp.adapters.MyViewPagerAdapter;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.fragments.SetingFrag;
import austin.mysakuraapp.fragments.SkrBunnpoFrag;
import austin.mysakuraapp.fragments.SkrTanngoFrag;
import austin.mysakuraapp.fragments.wordcenter.TanngoFrag;
import austin.mysakuraapp.viewfeature.IMainView;
import austin.mysakuraapp.views.lazyviewpager.LazyViewPager;

public class MainActivity extends BaseActivity implements View.OnClickListener, IMainView {
    private static MainActivity mForegroundActivity;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private LazyViewPager mViewPager;

    // TabLayout中的tab标题、侧边栏标题
    private String[] mTitles, mSlidTitles;
    private List<TextView> mSidebarMenus = new ArrayList<>();
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    private MyViewPagerAdapter mViewPagerAdapter;
    private TextView tvTitleWordCenter, tvTitleSkrBunnpo, tvTitleSkrTanngo, tvTitleSeting;
    private FragmentManager mFragManager;
    String defaultColor = "#CDD1D3", pressColor = "#FFFFFF";

    TanngoFrag tanngoFrag;
    SkrBunnpoFrag skrBunnpo;
    SkrTanngoFrag skrTango;
    SetingFrag setFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mForegroundActivity = this;
        GlobalParams.MAIN = this;
        mFragManager = getSupportFragmentManager();
        bindView();

        initData();
        configViews();
    }

    private void configViews() {
        refreshMenu(mSlidTitles);
        regSideListener();
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
     * 注册侧边栏条目点击监听事件
     */
    private void regSideListener() {
        for(int i=0;i<mSidebarMenus.size();i++){
            mSidebarMenus.get(i).setOnClickListener(new SideClickListener(i));
        }

    }

    class SideClickListener implements View.OnClickListener{
        private final int position;

        SideClickListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            // 判断当前哪个界面(Frag)处于显示状态,就把相应的侧边栏点击事件（连同角标）传过去让其自行处理
            if(GlobalParams.foreFrag instanceof TanngoFrag){
                   ((TanngoFrag) GlobalParams.foreFrag).replaceContentViewBySidePosition(position);
                return;
            }
            if(GlobalParams.foreFrag instanceof SkrBunnpoFrag){
                //TODO do sth;
                return;
            }
            if(GlobalParams.foreFrag instanceof SkrTanngoFrag){
                //TODO do sth;
                return;
            }
            if(GlobalParams.foreFrag instanceof SetingFrag){
                //TODO do sth;
                return;
            }
        }
    }

    /**
     * （初始化）刷新侧边栏数据
     *
     * @param mSlidTitles 侧边栏标题集合
     */
    private void refreshMenu(String[] mSlidTitles) {
        for (TextView tv : mSidebarMenus
                ) {
            tv.setText(null);
            tv.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < mSlidTitles.length; i++) {
            mSidebarMenus.get(i).setVisibility(View.VISIBLE);
            mSidebarMenus.get(i).setText(mSlidTitles[i]);
        }
    }

    /**
     * 渲染标题字体颜色
     *
     * @param i 当前选中的tab角标
     */
    private void setTitlePressIndex(int i) {

        tvTitleWordCenter.setTextColor(Color.parseColor(defaultColor));
        tvTitleSeting.setTextColor(Color.parseColor(defaultColor));
        tvTitleSkrTanngo.setTextColor(Color.parseColor(defaultColor));
        tvTitleSkrBunnpo.setTextColor(Color.parseColor(defaultColor));
        switch (i) {
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
        mSlidTitles = getResources().getStringArray(R.array.word_side);
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
//       TanngoFragOfNoun tanngoFragOfNoun = new TanngoFragOfNoun();
        tanngoFrag = new TanngoFrag();
        skrBunnpo = new SkrBunnpoFrag();
        skrTango = new SkrTanngoFrag();
        setFrag = new SetingFrag();
        mFragments.add(tanngoFrag);
        mFragments.add(skrBunnpo);
        mFragments.add(skrTango);
        mFragments.add(setFrag);
        tanngoFrag.setFragmentManager(mFragManager);
        GlobalParams.foreFrag = tanngoFrag;
    }

    @Override
    void bindView() {
        TextView item1 = (TextView) findViewById(R.id.item1);
        TextView item2 = (TextView) findViewById(R.id.item2);
        TextView item3 = (TextView) findViewById(R.id.item3);
        TextView item4 = (TextView) findViewById(R.id.item4);
        TextView item5 = (TextView) findViewById(R.id.item5);
        TextView item6 = (TextView) findViewById(R.id.item6);
        TextView item7 = (TextView) findViewById(R.id.item7);
        TextView item8 = (TextView) findViewById(R.id.item8);
        TextView item9 = (TextView) findViewById(R.id.item9);
        TextView item10 = (TextView) findViewById(R.id.item10);
        TextView item11 = (TextView) findViewById(R.id.item11);
        TextView item12 = (TextView) findViewById(R.id.item12);
        mSidebarMenus.add(item1);
        mSidebarMenus.add(item2);
        mSidebarMenus.add(item3);
        mSidebarMenus.add(item4);
        mSidebarMenus.add(item5);
        mSidebarMenus.add(item6);
        mSidebarMenus.add(item7);
        mSidebarMenus.add(item8);
        mSidebarMenus.add(item9);
        mSidebarMenus.add(item10);
        mSidebarMenus.add(item11);
        mSidebarMenus.add(item12);

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
        switch (v.getId()) {
            case R.id.tv_title_word:
                mViewPager.setCurrentItem(0);//切换页面
                refreshMenu(getResources().getStringArray(R.array.word_side));//刷新侧滑栏标题
                GlobalParams.foreFrag = tanngoFrag;//记录当前最顶端显示的frag
                break;
            case R.id.tv_title_skr_bunnpo:
                mViewPager.setCurrentItem(1);
                refreshMenu(getResources().getStringArray(R.array.bunnpo_side));
                GlobalParams.foreFrag = skrBunnpo;
                break;
            case R.id.tv_title_skr_tanngo:
                mViewPager.setCurrentItem(2);
                refreshMenu(getResources().getStringArray(R.array.sakura_side));
                GlobalParams.foreFrag = skrTango;
                break;
            case R.id.tv_title_seting:
                mViewPager.setCurrentItem(3);
                mDrawerLayout.closeDrawers();
                GlobalParams.foreFrag = setFrag;
                break;
        }
    }

    public static MainActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    public void showToolBar() {
        ObjectAnimator ani = ObjectAnimator.ofFloat(mToolbar, View.TRANSLATION_Y, -mToolbar.getHeight(), 0);
        ani.setDuration(500);
        ani.start();
    }

    @Override
    public void hideToolBar() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mToolbar, View.TRANSLATION_Y, 0, -mToolbar.getHeight());
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public boolean isToobarShow() {
        return mToolbar.isShown();
    }
}
