package austin.mysakuraapp.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.util.Log;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import austin.mysakuraapp.comm.GlobalParams;

public class AppUtil {
//	public static DbUtils db = DbUtils.create(GlobalParams.MAIN,
//			FileUtils.getDir(GlobalParams.MAIN, ""), "hifm.db");
	public static Date today;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
			Locale.getDefault());
	private static final String TAG = "AppUtil";

	private static final String APK_PATH = "/sdcard/hifm.apk";

	public static void installApp(Context context, String path) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");

		context.startActivity(intent);
	}

	/**
	 * 获取应用程序的版本信息
	 * 
	 * @param context
	 *            上下文
	 * @param packname
	 *            包名
	 * @return 版本号
	 */
	public static int getAppVersionCode(Context context, String packname) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(packname, 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * 获取应用程序的版本信息
	 * 
	 * @param context
	 *            上下文
	 * @return 版本号
	 */
	public static String getAppVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo info = pm.getApplicationInfo("com.austin.mynihonngo",
					PackageManager.GET_META_DATA);
			int id = info.uid;
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 获取当前应用程序的VersionName
		String packname = context.getPackageName();
		try {
			PackageInfo info = pm.getPackageInfo(packname, 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 查看是否有新版本
	 * 
	 * @param versionCode
	 * @param versionName
	 * @return true:需要更新false：不需要更新
	 */
	private static boolean checkWeatherNeedUpdate(String versionCode,
			String versionName) {
		String[] split1 = versionCode.split("\\.");
		String[] split2 = versionName.split("\\.");
		if (split1.length == 3 && split2.length == 3) {
			for (int i = 0; i < 3; i++) {
				int remote = Integer.valueOf(split1[i]);
				int local = Integer.valueOf(split2[i]);
				if (remote > local) {
					return true;
				} else if (remote == local) {
					continue;
				} else {
					return false;
				}
			}
			return false;
		} else {
			return false;
		}
	}

/*	private static void downloadApp(final Context context, String path) {
		HttpUtils http = new HttpUtils();
		HttpHandler handler = http.download(path, APK_PATH, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
				false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
				new RequestCallBack<File>() {

					private ProgressDialog dialog;

					@Override
					public void onStart() {
						// System.out.println("conn...");
						showProgressDialog();
					}

					*//**
					 * 显示升级提醒对话框
					 *//*
					private void showProgressDialog() {
						dialog = new ProgressDialog(context);
						dialog.setCancelable(false);
						dialog.setTitle("更新中...");
						// pDialog.setMessage("请稍后。。");
						// 设置进度条对话框//样式（水平，旋转）
						dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

						// 进度最大值
						// dialog.setMax(MAX_PROGRESS);

						// 显示
						dialog.show();

						// 必须设置到show之后
						// progress = (progress > 0) ? progress : 0;
						// dialog.setProgress(progress);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						System.out.println(current + "/" + total);

						dialog.setMax((int) total);
						dialog.setProgress((int) current);
					}

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						dialog.dismiss();

						// System.out.println("downloaded:"
						// + responseInfo.result.getPath());

						// 调用系统的程序安装器来安装apk
						installApp(context, responseInfo.result.getPath());
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						dialog.dismiss();

						System.out.println(msg);

						if (msg != null
								&& msg.contains("downloaded completely")) {
							// 调用系统的程序安装器来安装apk
							installApp(context, APK_PATH);
							return;
						}

						UIUtil.showTestToast("下载失败" + msg);

					}
				});

	}*/

	/**
	 * 判断应用是否在运行
	 * 
	 * @param pkgName
	 * @return
	 */
	private static boolean isRunning(String pkgName) {
		// 获取一个ActivityManager 对象
		ActivityManager activityManager = (ActivityManager) UIUtil.getContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取系统中所有正在运行的进程
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();
		// 对系统中所有正在运行的进程进行迭代，如果发现进程名，则return true
		for (RunningAppProcessInfo appProcessInfo : appProcessInfos) {
			String processName = appProcessInfo.processName;
			if (processName.equals(pkgName)) {
				return true;
			}
		}

		return false;
	}


	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					// System.out.println("Background App:");
					return true;
				} else {
					// System.out.println("Foreground App:");
					return false;
				}
			}
		}
		return false;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void moveToFront(Context context) {

		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		am.moveTaskToFront(GlobalParams.MAIN.getTaskId(), 0);
	}

	// To check if service is enabled
	private static boolean isAccessibilitySettingsOn(Context mContext) {
		int accessibilityEnabled = 0;
		final String service = "cn.hifm/cn.hifm.service.MyAccessibilityService";
		boolean accessibilityFound = false;
		try {
			accessibilityEnabled = Settings.Secure.getInt(mContext
					.getApplicationContext().getContentResolver(),
					Settings.Secure.ACCESSIBILITY_ENABLED);
			Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
		} catch (SettingNotFoundException e) {
			Log.e(TAG,
					"Error finding setting, default accessibility to not found: "
							+ e.getMessage());
		}

		TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(
				':');

		if (accessibilityEnabled == 1) {
			Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
			String settingValue = Settings.Secure.getString(mContext
					.getApplicationContext().getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (settingValue != null) {
				TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
				splitter.setString(settingValue);
				while (splitter.hasNext()) {
					String accessabilityService = splitter.next();

					Log.v(TAG, "-------------- > accessabilityService :: "
							+ accessabilityService);
					if (accessabilityService.equalsIgnoreCase(service)) {
						Log.v(TAG,
								"We've found the correct setting - accessibility is switched on!");
						return true;
					}
				}
			}

		} else {
			Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
		}

		return accessibilityFound;
	}

	/**
	 * 得到当前APP的uid
	 * 
	 * @return
	 */
	public static Integer getCurrAppUid() {
		PackageManager pm = GlobalParams.MAIN.getPackageManager();
		try {
			ApplicationInfo info = pm.getApplicationInfo(
					GlobalParams.MAIN.getPackageName(),
					PackageManager.GET_META_DATA);
			return info.uid;
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	}



	/**
	 * 一次WiFi从可用到不可用时间内WiFi流量
	 */
	public static long mWiFiStep = 0L;
	public static long mWiFiTotal;
	/**
	 * 进入WiFi代码块时记录当前的流量和(所有网络连接状态,只第一次进入时记录之)
	 */
	public static long mFirstInTotalStart = 0L;
	public static long mFirstInMobileStart = 0L;
	/**
	 * 出WiFi代码块时记录此时HiFM产生的总流量
	 */
	public static long mFirstInTotalEnd = 0L;
	public static long mFirstInMobileEnd = 0L;

	public static boolean isFirstInWiFi = true;



	/**
	 * 得到APP自安装之时至当前时刻产生的总流量
	 * 
	 * @return
	 */
	public static long getAllTraffic() {
		Integer appUid = getCurrAppUid();
		if (appUid != null) {
			long totalRxBytesW = TrafficStats.getUidRxBytes(appUid);
			// 获取发送的字节总数，包含Mobile和WiFi等.
			long totalTxBytesW = TrafficStats.getUidTxBytes(appUid);
			return totalRxBytesW + totalTxBytesW;
		}
		return 0L;
	}


	/*
	 * ----------------得到WiFi流量增量----------------------
	 */
	public static boolean isFirstIn = true;

	

	public static Long getWiFiTotalToday() {
		return null;
	}

	/*
	 * check if the App is installed
	 */
	public static boolean isAppInstalled(Context context, String packagename) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					packagename, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			// e.printStackTrace();
		}
		if (packageInfo == null) {
			// System.out.println("没有安装");
			return false;
		} else {
			// System.out.println("已经安装");
			return true;
		}
	}
}
