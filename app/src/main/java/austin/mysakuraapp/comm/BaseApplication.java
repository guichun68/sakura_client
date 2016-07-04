package austin.mysakuraapp.comm;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import java.util.Stack;

/**
 * @Description TODO
 * @author 
 * @date 2014-7-24 下午4:58:49
 */
public class BaseApplication extends MultiDexApplication {
	private final String TAG = "BaseApplication";

	/** 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了 */
	private static Context mInstance;
	/** 主线程ID */
	private static int mMainThreadId = -1;
	/** 主线程ID */
	private static Thread mMainThread;
	/** 主线程Handler */
	private static Handler mMainThreadHandler;
	/** 主线程Looper */
	private static Looper mMainLooper;

	private String userName = null;
	private String userSig = null;
	private boolean isBackground;
	//-------------GreenDao init--
	private SQLiteDatabase db;
	private Cursor cursor;

	/*@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}*/

	@Override
	public void onCreate() {
		Log.e(TAG,"diaoyongwoleme ");
//		Foreground.init(this);
		// System.out.println("---BaseApplication---onCreate()---run-------------------");
		// 异常捕获
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.init(getApplicationContext());
//		QALSDKManager.getInstance().init(this,2);
		mMainThreadId = android.os.Process.myTid();
		mMainThread = Thread.currentThread();
		mMainThreadHandler = new Handler();
		mMainLooper = getMainLooper();
		mInstance = this;

//		CrashHandler handler = CrashHandler.getInstance();
//        handler.init(getApplicationContext()); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
		// 配置友盟统计：禁止默认的Activity页面统计方式
		// MobclickAgent.openActivityDurationTrack(false);

		// 开启云巴
//		initYunba();
        //初始化NoHttp----start---
        NoHttp.init(this);
        Logger.setTag("NoHttpSample");
        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志
        //-----NoHttp end---------

		// 初始化讯飞语音
		// SpeechUtility.createUtility(this, SpeechConstant.APPID +
		// "=55754bf6");// 
//		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5635bf63");// 通过的

		// 配置LeanClound
		// 如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
//		AVOSCloud.initialize(this, "WThhh79s3mEDeeCLO3yWGmjA",
//				"18a6lYNUYKE4qVIHxoxl8sBF");

		super.onCreate();
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static Context getApplication() {
		return mInstance;
	}

	/** 获取主线程ID */
	public static int getMainThreadId() {
		return mMainThreadId;
	}

	/** 获取主线程 */
	public static Thread getMainThread() {
		return mMainThread;
	}

	/** 获取主线程的handler */
	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	/** 获取主线程的looper */
	public static Looper getMainThreadLooper() {
		return mMainLooper;
	}
	
	
	
	
	private static Stack<Activity> mActivityStack;
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}
	/**
	 * 结束所有Activity
	 */
	public void killAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
//				CommonUtil.LogOut("HBXAPP", "共杀死"+1+"个act");
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}
	

	/**
	 * 退出应用程序
	 */
	public void exitApp(Context context) {
		try {
			killAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public boolean getIsBackground(){
		return isBackground;
	}


}
