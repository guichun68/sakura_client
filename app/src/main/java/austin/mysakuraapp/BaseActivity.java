package austin.mysakuraapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.umeng.analytics.MobclickAgent;

import austin.mysakuraapp.comm.BaseApplication;


public class BaseActivity extends AppCompatActivity{
    private static BaseActivity mForegroundActivity;
    protected BaseApplication mApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (BaseApplication) BaseApplication.getApplication();
        this.mApp.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mForegroundActivity = this;

        // 配置友盟统计
        MobclickAgent.onResume(this); // 统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mForegroundActivity == this) {
            mForegroundActivity = null;
        }

        // 配置友盟统计
        MobclickAgent.onPause(this);
    }

    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }

}
