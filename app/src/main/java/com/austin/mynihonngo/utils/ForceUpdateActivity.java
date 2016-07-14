package com.austin.mynihonngo.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.comm.BaseApplication;
import com.austin.mynihonngo.engine.nohttp.CallServer;

public class ForceUpdateActivity extends Activity  {
	NotificationCompat.Builder mBuilder;
	/** Notification管理 */
	public NotificationManager mNotificationManager;
	private String desc,url;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
//			setBehindContentView(R.layout.frame_menu);
			desc = getIntent().getStringExtra("desc");
			url = getIntent().getStringExtra("url");
			mBuilder = new NotificationCompat.Builder(this);
			initNotify();
			showConfirmUpdateDialog();
		}
		/** 初始化通知栏 */
		private void initNotify() {
			mBuilder = new NotificationCompat.Builder(this);
			mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
					.setContentIntent(getDefalutIntent(0))
					// .setNumber(number)//显示数量
					.setPriority(0)// 设置该通知优先级
					// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
					.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				 	//.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
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
		
		private ProgressBar mProgressBar1;
		private Button mBtn_ok,mBtn_cancel;
		private TextView tv_desc;
		LinearLayout ll_btn;
		/**
		 * 显示，提示用户升级对话框
		 */
		protected void showConfirmUpdateDialog() {
//			AlertDialog.Builder adb = new AlertDialog.Builder(BaseActivity.getForegroundActivity());
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setCancelable(false);
			View view = View.inflate(this, R.layout.dialog_forceupdate, null);
			ll_btn = (LinearLayout) view.findViewById(R.id.ll_btn);
			mBtn_ok = (Button) view.findViewById(R.id.btn_ok);
			mBtn_cancel = (Button) view.findViewById(R.id.btn_cancel);
			mProgressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
			tv_desc = (TextView) view.findViewById(R.id.tv_desc);
			
			final AlertDialog dialog = adb.create();
			dialog.setView(view, 0,0,0,0);
			tv_desc.setText(desc);
			mBtn_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ll_btn.setVisibility(View.GONE);
					mProgressBar1.setVisibility(View.VISIBLE);
					
					downloadFile();
				}
			});
			mBtn_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.cancel();
					((BaseApplication)BaseApplication.getApplication()).exitApp(ForceUpdateActivity.this);
				}
			});
			dialog.show();
		
			int height = CommonUtil.getScreenHeight(ForceUpdateActivity.this);
			int width = CommonUtil.getScreenWidth(ForceUpdateActivity.this);
			WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
			params.width = width-50;
			params.height = height/2 ;
			dialog.getWindow().setAttributes(params);
		}


		/**
		 * 下载新版app
		 */
		protected void downloadFile() {

			//---------NoHttp--------------
			DownloadRequest sakuraGo = NoHttp.createDownloadRequest(url, Environment.getExternalStorageDirectory().getAbsolutePath(), "sakuraGo", true, false);
			CallServer.getDownloadInstance().add(0, sakuraGo, new DownloadListener() {
				@Override
				public void onDownloadError(int i, Exception e) {
//					UIUtil.showToastSafe("");
					UIUtil.showTestLog(ForceUpdateActivity.class.getSimpleName(),"下载未成功,请重试！");
					ll_btn.setVisibility(View.VISIBLE);
					mProgressBar1.setVisibility(View.GONE);
				}

				@Override
				public void onStart(int i, boolean b, long l, Headers headers, long l1) {

				}

				@Override
				public void onProgress(int what, int progress, long fileCount) {
					mProgressBar1.setMax((int) fileCount);
					mProgressBar1.setProgress(progress);
					mBuilder.setProgress((int) fileCount, progress, false);
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
//					UIUtil.showToastSafe("download cancelled");
					UIUtil.showTestLog(ForceUpdateActivity.class.getSimpleName(),"download cancelled");
				}
			});
			//---------End NoHttp


/*

			HttpUtils httpUtils = new HttpUtils();
			httpUtils.download(updateUrl, Environment.getExternalStorageDirectory() + "/temp.apk", new RequestCallBack<File>() {

				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					mBuilder.setContentText("下载完成");
					mNotificationManager.notify(0x0000ffff, mBuilder.build());
					
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					intent.addCategory("android.intent.category.DEFAULT");

					intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");

					startActivityForResult(intent, 0);
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					UIUtil.showToastSafe("下载未成功,请重试！");
					*/
/*mBtn_ok.setVisibility(View.VISIBLE);
					mBtn_cancel.setVisibility(View.VISIBLE);*//*

					
					ll_btn.setVisibility(View.VISIBLE);
					mProgressBar1.setVisibility(View.GONE);
					
					//ForceUpdateActivity.this.finish();
				}

				@Override
				public void onLoading(final long total, final long current, boolean isUploading) {
					mProgressBar1.setMax((int) total);
					mProgressBar1.setProgress((int) current);
					mBuilder.setProgress((int) total, (int) current, false);
					mNotificationManager.notify(0x0000ffff, mBuilder.build());
				}

			});
		}
*/
		}
}
