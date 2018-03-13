package com.austin.mynihonngo.fragments.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.adapters.NHGBaseAdapter;
import com.austin.mynihonngo.comm.ConstantValue;
import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.nohttp.CallServer;
import com.austin.mynihonngo.engine.nohttp.HttpListener;
import com.austin.mynihonngo.fragments.GrammarWebAct;
import com.austin.mynihonngo.model.bean.TSakuraGrammar;
import com.austin.mynihonngo.utils.UIManager;
import com.austin.mynihonngo.utils.UIUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 2017/1/5.
 * Desc:语法和文化
 */

public class GramAndCultureAct extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, HttpListener<JSONArray> {

    private static final String TAG = GramAndCultureAct.class.getSimpleName();
    private View view;
    private Integer mPageNo = 1;
    private ListView mLVGrammar;

    private SwipeRefreshLayout mSRLout;
//    private ImageView mIvBack;

    private MyBaseAdapter adapter;
    private List<TSakuraGrammar> mList= new ArrayList<TSakuraGrammar>(),mListTemp= new ArrayList<TSakuraGrammar>();//mListadapter的数据，mListTemp每次从网络获取的列表


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.act_gram_and_culture,null);
        setContentView(view);
        initView();
        getGrammarItemData(true);
    }

    @Override
    public void onRefresh() {
        getGrammarItemData(true);
    }

    public View initView() {
//        mIvBack = (ImageView) view.findViewById(R.id.iv_left);
        mLVGrammar = (ListView) view.findViewById(R.id.lv_grammar);
        mSRLout = (SwipeRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        mSRLout.setColorSchemeResources(R.color.blueStatus);
        mSRLout.setOnRefreshListener(this);
        adapter = new MyBaseAdapter(this,mList);
        mLVGrammar.setAdapter(adapter);
        mLVGrammar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//				Fragment target = getActivity().getSupportFragmentManager().findFragmentByTag(FragTAG.TAG_GRAMMAR_LIST_ITEM_DETAIL);
//				if(target==null){
//					target = new GrammarWebFragment();
//				}
//				Bundle bundle = new Bundle();
//				bundle.putString("url", GlobalParams.BASE_URL+((TSakuraGrammar)adapterView.getItemAtPosition(position)).getUrl());
//				UIManager.getInstance().changeFragmentAndSaveViews(GramAndCultureListFrag.this,target,true,bundle,FragTAG.TAG_GRAMMAR_LIST_ITEM_DETAIL);
                Intent intent = new Intent(GramAndCultureAct.this,GrammarWebAct.class);
                intent.putExtra("title",((TSakuraGrammar)adapterView.getItemAtPosition(position)).getTitle());
                intent.putExtra("url", GlobalParams.BASE_URL+((TSakuraGrammar)adapterView.getItemAtPosition(position)).getUrl());
                GramAndCultureAct.this.startActivity(intent);
            }
        });
        /*mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIManager.getInstance().popBackStack(1);
            }
        });*/
        return view;
    }


    private void getGrammarItemData(boolean isRefresh) {
        if(isRefresh){
            mList.clear();
            mPageNo=1;
        }else{
            mPageNo++;
        }
//		Request<JSONObject> request =  new FastJsonRequest(GlobalParams.URL_GET_GRAMMARS, RequestMethod.POST);
        Request<JSONArray> request = NoHttp.createJsonArrayRequest(GlobalParams.URL_GET_GRAMMARS, RequestMethod.POST);
        request.add("pageNo", mPageNo);
        CallServer.getRequestInstance().add(GramAndCultureAct.this, ConstantValue.WHAT_GET_GRAMMAR, request, this, true, true);
    }
    @Override
    public void onSucceed(int what, Response<JSONArray> response) {
        mSRLout.setRefreshing(false);
        JSONArray jsonArray = response.get();
        mListTemp = com.alibaba.fastjson.JSONArray.parseArray(jsonArray.toString(), TSakuraGrammar.class);
        if(!(mListTemp != null && mListTemp.size()>0)){
            UIUtil.showToastSafe("没有更多了");
            return;
        }
        mList.addAll(mListTemp);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        mSRLout.setRefreshing(false);
        UIUtil.showToastSafe(R.string.get_data_error);
        Log.e(TAG,"failed get data.");
        Log.e(TAG,exception.toString());
    }

    class MyBaseAdapter extends NHGBaseAdapter<TSakuraGrammar,ListView> {

        public MyBaseAdapter(Context context, List<TSakuraGrammar> list) {
            super(context, list,mLVGrammar);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = View.inflate(context, R.layout.item_grammar, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvGrammarTitle.setText(getItem(position).getTitle());
            return convertView;
        }
    }

    class ViewHolder{
        public final View root;
        public final TextView tvGrammarTitle;
        public ViewHolder(View root){
            this.root = root;
            this.tvGrammarTitle= (TextView) root.findViewById(R.id.tv_grammar_title);
        }
    }

}
