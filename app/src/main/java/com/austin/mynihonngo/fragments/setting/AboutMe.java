package com.austin.mynihonngo.fragments.setting;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.austin.mynihonngo.BaseActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.utils.AppUtil;
import com.austin.mynihonngo.utils.UIManager;


public class AboutMe extends BaseActivity {
	private static final String TAG = AboutMe.class.getSimpleName();
	private LinearLayout mLlGrammar;
	private AppCompatButton mBtnRefresh;
	private WebView mWebView;
	private FrameLayout loading_view;
	private String url;
	private Toolbar mToolbar;
	private ImageView ivBack;
	private TextView tvTitle;
	private TextView mTvAppVersion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_aboutme_web);
		initView();
	}

	public void initView() {
		url = GlobalParams.URL_ABOUT_ME;
		mTvAppVersion = (TextView) findViewById(R.id.tv_version_name);
		mTvAppVersion.setText(AppUtil.getAppVersionName(this));
		mBtnRefresh = (AppCompatButton) findViewById(R.id.refresh);
		mBtnRefresh.setVisibility(View.VISIBLE);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		ivBack = (ImageView) findViewById(R.id.iv_left);
		mLlGrammar = (LinearLayout) findViewById(R.id.ll_grammar);
		mToolbar = (Toolbar) findViewById(R.id.id_toolbar);

		tvTitle.setText("关于");
		mToolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIManager.getInstance().popBackStack(1);
			}
		});
		mBtnRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.reload();
			}
		});

		mWebView = (WebView) findViewById(R.id.wb_grammar_detail);
		loading_view = (FrameLayout) findViewById(R.id.loading_view);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				loading_view.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
/*		mWebView.getSettings().setJavaScriptEnabled(true);//支持js
		mWebView.getSettings().setPluginsEnabled(true);//设置webview支持插件*/
		WebSettings settings = mWebView.getSettings();
		// 设置网页的排列算法（Algorithm：算法） 为“单列结构”
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

		mWebView.loadUrl(url);

	}
}
