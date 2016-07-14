package com.austin.mynihonngo.fragments;

import android.os.Bundle;
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
import com.austin.mynihonngo.utils.UIManager;


public class GrammarWebAct extends BaseActivity {
	private static final String TAG = GrammarWebAct.class.getSimpleName();
	private LinearLayout mLlGrammar;
	private WebView mWebView;
	private FrameLayout loading_view;
	private String url;
	private Toolbar mToolbar;
	private ImageView ivBack;
	private TextView tvTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_grammar_web);
		initView();
	}

	String title="";
	public void initView() {
		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");

		tvTitle = (TextView) findViewById(R.id.tv_title);
		ivBack = (ImageView) findViewById(R.id.iv_left);
		mLlGrammar = (LinearLayout) findViewById(R.id.ll_grammar);
		mToolbar = (Toolbar) findViewById(R.id.id_toolbar);

		tvTitle.setText(title);
		mToolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIManager.getInstance().popBackStack(1);
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
/*		mTvRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				loading_view.setVisibility(View.VISIBLE);
				if(mWebView==null){
					mWebView = (WebView) view.findViewById(R.id.wb_grammar_detail);
				}
				mWebView.reload();
			}
		});*/

	}

/*	@Override
	public void onHiddenChanged(boolean hidden) {
		if(hidden){
			mWebView.reload();
		}
		super.onHiddenChanged(hidden);
	}*/

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		mLlGrammar.removeView(mWebView);
		mWebView.removeAllViews();
		mWebView.destroy();
		super.onDestroy();
	}
}
