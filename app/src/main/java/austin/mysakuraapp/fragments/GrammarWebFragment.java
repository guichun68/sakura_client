package austin.mysakuraapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import austin.mysakuraapp.R;
import austin.mysakuraapp.utils.UIManager;


public class GrammarWebFragment extends Fragment {
	private static final String TAG = GrammarWebFragment.class.getSimpleName();
	private LinearLayout mLlGrammar;
//	private TextView mTvTitle,mTvRefresh;
	private ImageButton mIbBack;
	private WebView mWebView;
	private FrameLayout loading_view;
	private String url;
	private Toolbar mToolbar;

	public View initView(LayoutInflater inflater) {
		Bundle bundle = getArguments();
		url = (String) bundle.get("url");

		View view = inflater.inflate(R.layout.frag_grammar_web, null);
		mLlGrammar = (LinearLayout) view.findViewById(R.id.ll_grammar);
		mToolbar = (Toolbar) view.findViewById(R.id.id_toolbar);

		mToolbar.setTitle(R.string.about_more_grammar);
		mToolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIManager.getInstance().popBackStack(1);
			}
		});

		mWebView = (WebView) view.findViewById(R.id.wb_grammar_detail);
		loading_view = (FrameLayout) view.findViewById(R.id.loading_view);
//		mTvRefresh = (TextView) view.findViewById(R.id.tv_right);
//		mTvRefresh.setVisibility(View.VISIBLE);
//		mTvTitle.setText(R.string.about_more_grammar);
		mIbBack.setVisibility(View.VISIBLE);
		// 用户点击了标题栏最左侧的回退按钮
		mIbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIManager.getInstance().popBackStack(1);
			}
		});
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				loading_view.setVisibility(View.GONE);
				super.onPageFinished(view, url);
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
		return view;
	}


	@Override
	public void onHiddenChanged(boolean hidden) {
		if(hidden){
			mWebView.reload();
		}
		super.onHiddenChanged(hidden);
	}

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
