package austin.mysakuraapp.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Window;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.error.ArgumentError;
import com.yolanda.nohttp.error.ClientError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.error.StorageSpaceNotEnoughError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import java.io.File;

import austin.mysakuraapp.R;
import austin.mysakuraapp.engine.nohttp.CallServer;

public class UpdateActivity extends Activity  {
	
	NotificationCompat.Builder mBuilder;
	/** Notification管理 */
	public NotificationManager mNotificationManager;
	private String desc,url;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			desc = getIntent().getStringExtra("desc");
			url = getIntent().getStringExtra("url");
			mBuilder = new NotificationCompat.Builder(this);
//			url = "http://m.apk.67mo.com/apk/999129_21769077_1443483983292.apk";
			initNotify();
			showConfirmUpdateDialog();
		}
		/** 初始化通知栏 */
		private void initNotify() {
//			mBuilder = new NotificationCompat.Builder(this);
			mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
					.setContentIntent(getDefalutIntent(0))
					// .setNumber(number)//显示数量
					.setPriority(0)// 设置该通知优先级
					// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
					.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				 	.setDefaults(Notification.DEFAULT_LIGHTS)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
					// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
					// requires VIBRATE permission
					.setSmallIcon(R.mipmap.ic_launcher);
			
			mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		}
		/**
		 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
		 * @flags属性:  
		 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
		 * 点击去除： Notification.FLAG_AUTO_CANCEL 
		 */
		public PendingIntent getDefalutIntent(int flags){
			PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
			return pendingIntent;
		}
		/**
		 * 显示，提示用户升级对话框
		 */
		protected void showConfirmUpdateDialog() {
			//对话框，他是activity的一部分。

			android.support.v7.app.AlertDialog.Builder adb = new android.support.v7.app.AlertDialog.Builder(this);
			adb.setTitle("升级提醒");
			adb.setMessage(desc);
//			adb.setCancelable(false);
			adb.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					UpdateActivity.this.finish();
				}
			});
			
			adb.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					downloadFile();
					UpdateActivity.this.finish();
				}
			});

			adb.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//跳转至主页面
					UpdateActivity.this.finish();
				}
			});
			
			adb.show();
		}
		/**
		 * 下载新版app
		 * 
		 */
		protected void downloadFile() {

			//---------NoHttp--------------
			DownloadRequest sakuraGo = NoHttp.createDownloadRequest(url,
					FileUtil2.getRootPath().getAbsolutePath(), "sakuraGo", true, false);
			CallServer.getDownloadInstance().add(0, sakuraGo, new DownloadListener() {
				@Override
				public void onDownloadError(int i, Exception exception) {
					String message = "下载出错了：";
					if (exception instanceof ClientError) {
						message += "客户端错误";
					} else if (exception instanceof ServerError) {
						message += "服务器发生内部错误";
					} else if (exception instanceof NetworkError) {
						message += "网络不可用，请检查网络";
					} else if (exception instanceof StorageReadWriteError) {
						message += "存储卡错误，请检查存储卡";
					} else if (exception instanceof StorageSpaceNotEnoughError) {
						message += "存储位置空间不足";
					} else if (exception instanceof TimeoutError) {
						message += "下载超时";
					} else if (exception instanceof UnKnownHostError) {
						message += "服务器找不到";
					} else if (exception instanceof URLError) {
						message += "url地址错误";
					} else if (exception instanceof ArgumentError) {
						message += "下载参数错误";
					} else {
						message += "未知错误";
					}
					UIUtil.showToastSafe(message);
					exception.printStackTrace();
					UpdateActivity.this.finish();
				}
				@Override
				public void onStart(int i, boolean b, long l, Headers headers, long l1) {
					UIUtil.showToastSafe("正在下载,请稍后");
				}
				@Override
				public void onProgress(int what, int progress, long fileCount) {
					mBuilder.setProgress((int) fileCount, (int)fileCount*progress/100, false);
					mNotificationManager.notify(0x0000ffff, mBuilder.build());
				}

				@Override
				public void onFinish(int what, String filePath) {
					mBuilder.setContentText("下载完成");
					mNotificationManager.notify(0x0000ffff, mBuilder.build());
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					intent.addCategory("android.intent.category.DEFAULT");

					intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");

					startActivityForResult(intent, 0);
				}
				@Override
				public void onCancel(int i) {
					UIUtil.showToastSafe("download cancelled");
				}
			});
		}
}
