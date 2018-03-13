package com.austin.mynihonngo;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.austin.mynihonngo.adapters.MyViewPagerAdapter;
import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.MyActionBarDrawerToggle;
import com.austin.mynihonngo.fragments.SkrBunnpo.SkrBunnpoFrameFrag;
import com.austin.mynihonngo.fragments.setting.SetingFrag;
import com.austin.mynihonngo.fragments.skrTanngo.SkrTanngoFrameFrag;
import com.austin.mynihonngo.fragments.wordcenter.TanngoFrameFrag;
import com.austin.mynihonngo.utils.UIUtil;
import com.austin.mynihonngo.utils.UpdateService;
import com.austin.mynihonngo.viewfeature.IMainView;
import com.austin.mynihonngo.views.lazyviewpager.LazyViewPager;

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
    String defaultColor = "#CDD1D3", pressColor = "#FFFFFF";

    TanngoFrameFrag tanngoFrameFrag;
    SkrBunnpoFrameFrag skrBunnpo;
    SkrTanngoFrameFrag skrTango;
    SetingFrag setFrag;
    private LinearLayout mLlContent;
    private MyActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mForegroundActivity = this;
        GlobalParams.MAIN = this;
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
        mActionBarDrawerToggle =
                new MyActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);

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
                switchFrag(position);
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
            for(int i=0;i<mSidebarMenus.size();i++){//将侧边栏所有字体颜色还原成灰白色
                mSidebarMenus.get(i).setTextColor(getResources().getColor(R.color.text_bg));
            }
            mSidebarMenus.get(position).setTextColor(getResources().getColor(R.color.slideMenuTextClickedColor));
            //关闭侧边栏
            mDrawerLayout.closeDrawer(GravityCompat.START);
            // 判断当前哪个界面(Frag)处于显示状态,就把相应的侧边栏点击事件（连同角标）传过去让其自行处理
            if(GlobalParams.foreFrag instanceof TanngoFrameFrag){
                ((TanngoFrameFrag) GlobalParams.foreFrag).replaceContentViewBySidePosition(position);
                return;
            }
            if(GlobalParams.foreFrag instanceof SkrBunnpoFrameFrag){
                //TODO do sth;
                ((SkrBunnpoFrameFrag) GlobalParams.foreFrag).replaceContentViewBySidePosition(position);
                return;
            }
            if(GlobalParams.foreFrag instanceof SkrTanngoFrameFrag){
                //TODO do sth;
                ((SkrTanngoFrameFrag) GlobalParams.foreFrag).replaceContentViewBySidePosition(position);
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

        tanngoFrameFrag = new TanngoFrameFrag();
        skrBunnpo = new SkrBunnpoFrameFrag();
        skrTango = new SkrTanngoFrameFrag();
        setFrag = new SetingFrag();
        mFragments.add(tanngoFrameFrag);
        mFragments.add(skrBunnpo);
        mFragments.add(skrTango);
        mFragments.add(setFrag);

        GlobalParams.foreFrag = tanngoFrameFrag;
    }

    void bindView() {
        mLlContent = (LinearLayout) findViewById(R.id.ll_drawer);
        TextView item1 = (TextView) findViewById(R.id.item1);
        if(item1 != null)item1.setTextColor(getResources().getColor(R.color.slideMenuTextClickedColor));
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
        if(GlobalParams.isDrawerOpened){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        switch (v.getId()) {
            case R.id.tv_title_word://切换到了单词中心
                mViewPager.setCurrentItem(0);//切换页面
                switchFrag(0);
                break;
            case R.id.tv_title_skr_bunnpo://切换到了文法页
                mViewPager.setCurrentItem(1);
                switchFrag(1);
                break;
            case R.id.tv_title_skr_tanngo://切换到了樱花单词中心
                mViewPager.setCurrentItem(2);
                switchFrag(2);
                break;
            case R.id.tv_title_seting://切换到了设置页
                mViewPager.setCurrentItem(3);
                switchFrag(3);
                break;
        }
    }

    /**
     * 更新toggle按钮（不同界面控制其显隐，并且控制监听（包含手势滑动是否可用）;
     */
    private void refreshToggle(int viewRes) {
        boolean isAlreadyAdded = false;
        switch (viewRes){
            case R.id.tv_title_word://切换到了单词中心
                for(int i=0;i<mDrawerLayout.getChildCount();i++){
                    if(mDrawerLayout.getChildAt(i).getId()==R.id.ll_drawer){
                        isAlreadyAdded = true;
                    }
                }
                if(!isAlreadyAdded)mDrawerLayout.addView(mLlContent);
                mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
                mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                break;
            case R.id.tv_title_skr_bunnpo://切换到了文法页
                for(int i=0;i<mDrawerLayout.getChildCount();i++){
                    if(mDrawerLayout.getChildAt(i).getId()==R.id.ll_drawer){
                        isAlreadyAdded = true;
                    }
                }
                if(!isAlreadyAdded)mDrawerLayout.addView(mLlContent);
                mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
                mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                break;
            case R.id.tv_title_skr_tanngo://切换到了樱花单词中心
                for(int i=0;i<mDrawerLayout.getChildCount();i++){
                    if(mDrawerLayout.getChildAt(i).getId()==R.id.ll_drawer){
                        isAlreadyAdded = true;
                    }
                }
                if(!isAlreadyAdded)mDrawerLayout.addView(mLlContent);
                mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
                mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                break;
            case R.id.tv_title_seting://切换到了设置页
                for(int i=0;i<mDrawerLayout.getChildCount();i++){
                    if(mDrawerLayout.getChildAt(i).getId()==R.id.ll_drawer){
                        isAlreadyAdded = true;
                    }
                }
                if(isAlreadyAdded)mDrawerLayout.removeView(mLlContent);
                mDrawerLayout.removeDrawerListener(mActionBarDrawerToggle);
                mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                break;
        }
    }

    public static MainActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    public void showToolBar() {
/*        ObjectAnimator ani = ObjectAnimator.ofFloat(mToolbar, View.TRANSLATION_Y, -mToolbar.getHeight(), 0);
        ani.setDuration(500);
        ani.start();*/
        mToolbar.setVisibility(View.VISIBLE);
    }

//    Handler handler = new Handler();

    @Override
    public void hideToolBar() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mToolbar, View.TRANSLATION_Y, 0, -mToolbar.getHeight());
        animator.setDuration(500);
        animator.start();
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //显示dialog
                mToolbar.setVisibility(View.GONE);
            }
        }, 500);   //500毫秒*/
    }

    public void switchFrag(int position){
        saveSideMenuPosition();
        for(int i=0;i<mSidebarMenus.size();i++){//将侧边栏所有字体颜色还原成灰白色
            mSidebarMenus.get(i).setTextColor(getResources().getColor(R.color.text_bg));
        }

        switch (position){
            case 0://单词中心页
                refreshMenu(getResources().getStringArray(R.array.word_side));//刷新侧滑栏标题
                //还原上次离开本页面时的角标的字体颜色
                mSidebarMenus.get(GlobalParams.tanngoFragSidePostion).setTextColor(getResources().getColor(R.color.slideMenuTextClickedColor));
                GlobalParams.foreFrag = tanngoFrameFrag;//记录当前最顶端显示的frag
                refreshToggle(R.id.tv_title_word);
                break;
            case 1://(樱花)语法页
                refreshMenu(getResources().getStringArray(R.array.bunnpo_side));
                //还原上次离开本页面时的角标的字体颜色
                mSidebarMenus.get(GlobalParams.skrBunnpoSidePosition).setTextColor(getResources().getColor(R.color.slideMenuTextClickedColor));
                if(GlobalParams.isFirstComeInSkrBunnpo){
                    skrBunnpo.replaceContentViewBySidePosition(0);
                    GlobalParams.isFirstComeInSkrBunnpo = false;
                }
                GlobalParams.foreFrag = skrBunnpo;
                refreshToggle(R.id.tv_title_skr_bunnpo);
                break;
            case 2://樱花单词页
                refreshMenu(getResources().getStringArray(R.array.sakura_side));
                //还原上次离开本页面时的角标的字体颜色
                mSidebarMenus.get(GlobalParams.skrTanngoSidePosition).setTextColor(getResources().getColor(R.color.slideMenuTextClickedColor));

                if(GlobalParams.isFirstComeInSkrTanngo){
                    skrTango.replaceContentViewBySidePosition(0);
                    GlobalParams.isFirstComeInSkrTanngo = false;
                }
                GlobalParams.foreFrag = skrTango;
                refreshToggle(R.id.tv_title_skr_tanngo);
                break;
            case 3://设置页
                refreshToggle(R.id.tv_title_seting);
                GlobalParams.foreFrag = setFrag;
                mDrawerLayout.closeDrawers();
                break;
        }

    }

    /**
     *
     * 切换页面之前先保存当前页面的选中的侧边栏角标，以便下次还原时正确还原指定内容
     */
    private void saveSideMenuPosition() {
        if(GlobalParams.foreFrag instanceof SkrTanngoFrameFrag){
            GlobalParams.skrTanngoSidePosition = skrTango.getCurrSlidePosition();
        }else if(GlobalParams.foreFrag instanceof TanngoFrameFrag){
            GlobalParams.tanngoFragSidePostion = tanngoFrameFrag.getCurrSlidePosition();
        }else if(GlobalParams.foreFrag instanceof SkrBunnpoFrameFrag){
            GlobalParams.skrBunnpoSidePosition = skrBunnpo.getCurrSlidePosition();
        }
    }

    @Override
    public boolean isToobarShow() {
        return mToolbar.isShown();
    }

  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            showExitDialog();
        }
        return super.onKeyDown(keyCode, event);
    }*/
    //确定要退出App?
    private boolean isConfirmExitApp = false;
    @Override
    public void onBackPressed() {
        //返回键逻辑：首先检查fragment回退栈中(只保存有在程序运行后打开的frag)是否有打开的fragment，有则按照系统的返回键默认处理，
        //没有则说明已经返回到了首页,此时显示退出确认框。
        if(getSupportFragmentManager().getBackStackEntryCount()>=1){
            super.onBackPressed();
            return;
        }
        if(!isConfirmExitApp){
//            showExitDialog();
            twoClick();
        }else{
            super.onBackPressed();
        }
    }
    long[] mHits = new long[2];
    /**
     * 两次快速点击
     */
    private void twoClick() {
        UIUtil.showToastSafe(R.string.moreclickexit);
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            isConfirmExitApp = true;
            GlobalParams.isFirstComeInSkrTanngo = true;
            GlobalParams.isFirstComeInSkrBunnpo = true;
            GlobalParams.isCheckedUpdate = false;
            MainActivity.this.onBackPressed();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("确定退出?");
        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isConfirmExitApp = true;
                GlobalParams.isFirstComeInSkrTanngo = true;
                GlobalParams.isFirstComeInSkrBunnpo = true;
                GlobalParams.isCheckedUpdate = false;
                MainActivity.this.onBackPressed();
            }
        });
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isConfirmExitApp = false;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!GlobalParams.isCheckedUpdate) {
            checkUpdate();
        }
    }
    private String TAG = MainActivity.class.getSimpleName();
    public UpdateService.MyBinder mBinder;
    private ServiceConnection myServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.e(TAG,"Update service is Connected.");
            mBinder = (UpdateService.MyBinder) service;
            mBinder.callCheckUpdate(null);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"Update service is Disconnected.");
        }
    };
    /**
     * 启动服务检查更新
     */
    private void checkUpdate() {
        Intent in = new Intent(MainActivity.this, UpdateService.class);
        bindService(in, myServiceConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myServiceConn) {
            unbindService(myServiceConn);
            myServiceConn = null;
        }
    }
}
