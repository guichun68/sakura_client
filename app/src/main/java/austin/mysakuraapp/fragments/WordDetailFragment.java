package austin.mysakuraapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ArgumentKey;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.model.bean.WordResult;
import austin.mysakuraapp.utils.NetworkUtil;
import austin.mysakuraapp.utils.StringUtil;
import austin.mysakuraapp.utils.UIManager;
import austin.mysakuraapp.utils.UIUtil;


public class WordDetailFragment extends Fragment implements OnClickListener{
	private View view;
	//返回箭头、朗读单词图标、
	private ImageView ivLeft,ivSpeekWord;
	private TextView tvWord;//单词
	private TextView tvKana;//假名
	private TextView tvSpeechType;//词性
	private TextView tvWdCn;//单词中文意思
	private TextView tvSentenceEgSpeek;//例句朗读
	private TextView tvSentence;//例句
	private TextView tvShade;//例句翻译上方遮罩
	private TextView tvSentenceCn;//例句翻译
	private TextView tvExtendWord;//引申单词
	private TextView tvExtendWordCn;//引申单词含义
	private TextView tvOther;//其他说明
	private ImageView ivNextWord,ivPreWord;
	private LinearLayout mLlSentence,mLlExtendWord,mLlOther;//例句、引申、其他说明
	private TextView mTvTone;

	private WordResult mWordResult;
	private int currWdPosition;//当前单词所在list的角标
	//朗读的实现原理：本朗读使用了百度接口，示例网址：http://tts.baidu.com/text2audio?lan=jp&ie=UTF-8&spd=2&text=%E6%9E%97%E6%AA%8E
	//所以使用webView加载上述网址，并且自动播放，实现朗读功能。
	private WebView wv,wv2;//分别代表单词朗读的webVeiw和例句的朗读webView
	private MyWebClient myClient;
	private MyWebClient2 myClient2;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_word_detail, container,false);
		bindView();
		configView();
		initData();
		return view;
	}

	private void bindView() {
		wv = (WebView) view.findViewById(R.id.wv);
		wv2 = (WebView) view.findViewById(R.id.wv2);
		mLlSentence = (LinearLayout) view.findViewById(R.id.ll_two);
		mTvTone = (TextView) view.findViewById(R.id.tv_tone);
		ivLeft = (ImageView) view.findViewById(R.id.iv_left);
		tvWord = (TextView) view.findViewById(R.id.tv_word);
		tvKana = (TextView) view.findViewById(R.id.tv_kana);
		ivSpeekWord = (ImageView) view.findViewById(R.id.iv_speek);
		tvSpeechType = (TextView) view.findViewById(R.id.tv_speech_type);
		tvWdCn = (TextView) view.findViewById(R.id.tv_wd_cn);
		tvSentenceEgSpeek = (TextView) view.findViewById(R.id.tv_sentence_eg_speek);
		tvSentence = (TextView) view.findViewById(R.id.tv_sentence);
		tvShade = (TextView) view.findViewById(R.id.tv_shade);
		tvSentenceCn = (TextView) view.findViewById(R.id.tv_sentence_cn);
		tvExtendWord = (TextView) view.findViewById(R.id.tv_extend_word);
		tvExtendWordCn = (TextView) view.findViewById(R.id.tv_extend_word_cn);
		tvOther = (TextView) view.findViewById(R.id.tv_other);
		ivNextWord = (ImageView) view.findViewById(R.id.iv_next_word);
		ivPreWord = (ImageView) view.findViewById(R.id.iv_pre_word);
		mLlExtendWord = (LinearLayout) view.findViewById(R.id.ll_extend_word);
		mLlOther = (LinearLayout) view.findViewById(R.id.ll_other);

	}

	/**
	 * String wd_extend_others = mWordResult.getWd_extend_others();
			String others = wd_extend_others.replace("|", "\n");//将文本中的“|”变成换行
			tvOther.setText(others);
	 */
	public View configView() {
		ivLeft.setOnClickListener(this);
		ivSpeekWord.setOnClickListener(this);
		tvSentenceEgSpeek.setOnClickListener(this);
		tvShade.setOnClickListener(this);
		ivNextWord.setOnClickListener(this);
		ivPreWord.setOnClickListener(this);
		myClient = new MyWebClient();
		myClient2 = new MyWebClient2();
		wv.setWebViewClient(myClient);
		wv2.setWebViewClient(myClient2);
		return view;
	}

	public void initData() {
		Bundle bundle = getArguments();
		this.mWordResult = (WordResult) bundle.get(ArgumentKey.WordArguBundleKey);
		this.currWdPosition = bundle.getInt(ArgumentKey.position);
		refreshUI();
	}
	//由点击了“上一个”或“下一个”单词后触发
	public void refreshUIAdv(int currPosition,WordResult wordResult){
		this.mWordResult = wordResult;
		this.currWdPosition = currPosition;
		refreshUI();
	}

	public void refreshUI() {
		//发现下面的loadURl方法针对5.0以上才有效
//		wv.loadUrl(ConstantValue.BaiduTTSBaseURL+mWordResult.getWd_name());
//	    wv2.loadUrl(ConstantValue.BaiduTTSBaseURL+mWordResult.getWd_sentence_eg());
		if (SDK_INT <= 16) {
			ivSpeekWord.setVisibility(View.INVISIBLE);
			wv.loadUrl("");
			wv2.loadUrl("");
		}else{
			wv.loadData(getHtml(mWordResult.getWd_name()),"text/html","UTF-8");
			wv2.loadData(getHtml(mWordResult.getWd_sentence_eg()),"text/html","UTF-8");
		}
		mTvTone.setText("");
		tvWord.setText(mWordResult.getWd_name());
		tvKana.setText(mWordResult.getWd_kana());
		tvSpeechType.setText("【"+mWordResult.getWd_part_speech_name()+"】");
		mTvTone.setText(StringUtil.getToneStr(getActivity(),mWordResult.getWd_tone()));
		if(StringUtil.isEmpty( mWordResult.getWd_name_cn())){
			tvWdCn.setText("");
		}else{
			String tvWdCnStr = mWordResult.getWd_name_cn().replace("|", "\n");//将文本中的“|”变成换行
			tvWdCn.setText(tvWdCnStr);
		}
		
		if(StringUtil.isEmpty(mWordResult.getWd_sentence_eg())){
			tvSentence.setVisibility(View.INVISIBLE);
			tvShade.setVisibility(View.INVISIBLE);
		}else{
			tvSentence.setVisibility(View.VISIBLE);
			tvShade.setVisibility(View.VISIBLE);
			tvSentence.setText(""+mWordResult.getWd_sentence_eg());
		}
		tvSentenceCn.setText(StringUtil.getNoVertiLineStr(mWordResult.getWd_sentence_cn()));
		if(StringUtil.isEmpty(mWordResult.getWd_sentence_eg())&& StringUtil.isEmpty(mWordResult.getWd_sentence_cn())){
			mLlSentence.setVisibility(View.GONE);
		}else{
			mLlSentence.setVisibility(View.VISIBLE);
		}
		if(StringUtil.isEmpty(mWordResult.getWd_extend_word())&&StringUtil.isEmpty(mWordResult.getWd_extend_word_cn())){
			mLlExtendWord.setVisibility(View.GONE);
		}else{
			mLlExtendWord.setVisibility(View.VISIBLE);
		}
		tvExtendWord.setText(StringUtil.getNoVertiLineStr(mWordResult.getWd_extend_word()));
		tvExtendWordCn.setText(StringUtil.getNoVertiLineStr(mWordResult.getWd_extend_word_cn()));

		if(StringUtil.isEmpty(mWordResult.getWd_extend_others())){
			mLlOther.setVisibility(View.GONE);
		}else{
			mLlOther.setVisibility(View.VISIBLE);
			String wd_extend_others = mWordResult.getWd_extend_others();
			tvOther.setText(StringUtil.getNoVertiLineStr(wd_extend_others));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_left:
			UIManager.getInstance().popBackStack(1);
			break;
		case R.id.iv_speek://单词朗读
			speek(R.id.iv_speek);
			break;
		case R.id.tv_sentence_eg_speek://例句朗读

			speek(R.id.tv_sentence_eg_speek);
			break;
		case R.id.tv_shade://例句翻译遮罩
			tvShade.setVisibility(View.INVISIBLE);
			break;
		case R.id.iv_next_word://转到下一个单词
			if(GlobalParams.iWord != null){
				GlobalParams.iWord.openNextWordDetailFrg(currWdPosition);
			}else{
				throw new RuntimeException("GloalParams.iFragmentListener is null.");
			}
			break;
		case R.id.iv_pre_word://转到上一个单词
			if(GlobalParams.iWord != null){
				GlobalParams.iWord.openPreWordDetailFrg(currWdPosition);
			}else{
				throw new RuntimeException("GloalParams.iFragmentListener is null.");
			}
			break;
		}
	}
	int SDK_INT=android.os.Build.VERSION.SDK_INT;;
	//朗读 单词 或者 例句
	private void speek(int viewId) {
		if(!NetworkUtil.checkNetwork(getActivity())){
			UIUtil.showToastSafe(R.string.hintCheckNet);
			return;
		}
		switch (viewId)
		{
			case R.id.iv_speek:
				if(wv==null){
					wv = (WebView) view.findViewById(R.id.wv);
				}
				if (SDK_INT > 16) {
					wv.getSettings().setMediaPlaybackRequiresUserGesture(false);
				}
				wv.reload();
				break;
			case R.id.tv_sentence_eg_speek:
				if(wv2==null){
					wv2 = (WebView) view.findViewById(R.id.wv2);
				}
				if (SDK_INT > 16) {
					wv2.getSettings().setMediaPlaybackRequiresUserGesture(false);
				}
				wv2.reload();
				break;
		}


	}

	class MyWebClient extends WebViewClient{
		@Override
		public void onPageFinished(WebView view, String url) {
			int SDK_INT = android.os.Build.VERSION.SDK_INT;
			if (SDK_INT > 16) {
				wv.getSettings().setMediaPlaybackRequiresUserGesture(true);
			}
		}
	}
	class MyWebClient2 extends WebViewClient{
		@Override
		public void onPageFinished(WebView view, String url) {
			int SDK_INT = android.os.Build.VERSION.SDK_INT;
			if (SDK_INT > 16) {
				wv2.getSettings().setMediaPlaybackRequiresUserGesture(true);
			}
		}
	}

	public String getHtml(String word){
		String html = "<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
				"<head>\n" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
				"<title>title</title>\n" +
				"<body>\n" +
				"<audio src=\"http://tts.baidu.com/text2audio%3flan=jp&ie=UTF-8&spd=2&text="+word+"\"\n" +
				"       controls=\"controls\" autoplay=\"true\"/>\n" +
				"</body>\n" +
				"</html>";
		return html;
	}


}
