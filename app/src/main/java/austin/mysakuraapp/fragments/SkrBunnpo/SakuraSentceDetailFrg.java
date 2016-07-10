package austin.mysakuraapp.fragments.SkrBunnpo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ArgumentKey;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.fragments.GrammarWebFragment;
import austin.mysakuraapp.model.bean.SakuraResult;
import austin.mysakuraapp.utils.StringUtil;
import austin.mysakuraapp.utils.UIManager;


/**樱花JP 单个句子详解
 * @author Austin
 *
 */
public class SakuraSentceDetailFrg extends Fragment implements OnClickListener {

	private View view;
	private SakuraResult mSakuraResult;
	public FragmentManager mFrgManager;
    // Content View Elements
    private ImageView mIv_left;
    private TextView mTv_sentence;
    private TextView mTv_shiyi;
    private TextView mTv_sentence_cn;
    private TextView mTv_parse_tip;
    private TextView mTv_parse;
    private ImageView ivNextWord,ivPreWord;
	private int currSentencePosition;//当前例句所在list的角标
	private TextView mTvMore;
	
	private TextView mTv_sentence_cn_shade,tv_shade;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_sentence_detail,container,false);
		bindViews();
		initData();
		return view;
	}

	private void bindViews() {
    	mTv_sentence_cn_shade = (TextView) view.findViewById(R.id.tv_sentence_cn_shade);
		mTvMore = (TextView) view.findViewById(R.id.tv_more_url);
    	mTv_sentence_cn_shade.setClickable(true);
    	mTv_sentence_cn_shade.setOnClickListener(this);
    	tv_shade = (TextView) view.findViewById(R.id.tv_sentence_shade);
        mIv_left = (ImageView) view.findViewById(R.id.iv_left);
        mTv_sentence = (TextView) view.findViewById(R.id.tv_sentence);
        mTv_shiyi = (TextView) view.findViewById(R.id.tv_shiyi);
        mTv_sentence_cn = (TextView) view.findViewById(R.id.tv_sentence_cn);
        mTv_parse_tip = (TextView) view.findViewById(R.id.tv_parse_tip);
        mTv_parse = (TextView) view.findViewById(R.id.tv_parse);
        ivNextWord = (ImageView) view.findViewById(R.id.iv_next_word);
        ivPreWord = (ImageView) view.findViewById(R.id.iv_pre_word);
        ivNextWord.setOnClickListener(this);
        ivPreWord.setOnClickListener(this);
        mIv_left.setOnClickListener(this);
    }

	public void initData() {
		Bundle bundle = getArguments();
		mSakuraResult = (SakuraResult) bundle.get(ArgumentKey.SentenceArguBundleKey);
		this.currSentencePosition = bundle.getInt(ArgumentKey.position);
		refreshUIAdv(currSentencePosition, mSakuraResult);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_left:
			UIManager.getInstance().popBackStack(1);
			break;
		case R.id.iv_next_word:
			if(GlobalParams.iFragmentListenerSakuraGrammar != null){
				GlobalParams.iFragmentListenerSakuraGrammar.openNextWordDetailFrg(currSentencePosition);
			}else{
				throw new RuntimeException("GloalParams.iFragmentListener is null.");
			}			
			break;
		case R.id.iv_pre_word:
			if(GlobalParams.iFragmentListenerSakuraGrammar != null){
				GlobalParams.iFragmentListenerSakuraGrammar.openPreWordDetailFrg(currSentencePosition);
			}else{
				throw new RuntimeException("GloalParams.iFragmentListener is null.");
			}			
			break;
		case R.id.tv_sentence_cn_shade:
			tv_shade.setVisibility(View.INVISIBLE);
			mTv_sentence_cn_shade.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
	}

	public void refreshUIAdv(int currPosition, SakuraResult sr) {
		this.mSakuraResult = sr;
		this.currSentencePosition = currPosition;
		refreshUI();	
	}
	public void refreshUI() {
		tv_shade.setVisibility(View.VISIBLE);
		
		String sentenceJp = mSakuraResult.getSkrSentenceJP().replace("|", "\n——");
		String sentenceCn = mSakuraResult.getSkrSentenceCn().replace("|", "\n——");
		
		
		if(GlobalParams.showNihonngo){
			mTv_sentence.setText(sentenceJp);
			mTv_sentence_cn.setText(sentenceCn);
			mTv_sentence_cn_shade.setText(sentenceCn);
		}else{
			mTv_sentence.setText(sentenceCn);
			mTv_sentence_cn.setText(sentenceJp);
			mTv_sentence_cn_shade.setText(sentenceJp);
		}
		mTv_sentence_cn_shade.setVisibility(View.VISIBLE);
		String tvParseStr =mSakuraResult.getSkrParse();
		if(!StringUtil.isEmpty(tvParseStr)){
			String formatTvParse = tvParseStr.replace("|", "<br/>");//将文本中的“|”变成换行
			mTv_parse.setText(Html.fromHtml(formatTvParse));
		}else{
			mTv_parse.setText("");
		}
		if(mSakuraResult.getGrammar()==null){
			mTvMore.setVisibility(View.INVISIBLE);
		}else{
			mTvMore.setVisibility(View.VISIBLE);
		}
		mTvMore.setClickable(true);
		mTvMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mFrgManager ==null){
					mFrgManager = getActivity().getSupportFragmentManager();
				}
				Fragment target = mFrgManager.findFragmentByTag(ConstantValue.FRAG_TAG_GRAMMAR);
				if(target == null){
					target = new GrammarWebFragment();
				}
				Bundle bundle = new Bundle();
				bundle.putString("url",GlobalParams.BASE_URL+mSakuraResult.getGrammar().getUrl());
				UIManager.getInstance().changeFragmentAndSaveViews(SakuraSentceDetailFrg.this,target,true,bundle,ConstantValue.FRAG_TAG_GRAMMAR);
			}
		});
	}

}
