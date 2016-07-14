package com.austin.mynihonngo.fragments.SkrBunnpo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.austin.mynihonngo.MainActivity;
import com.austin.mynihonngo.R;
import com.austin.mynihonngo.adapters.WordRecyclerViewAdapter;
import com.austin.mynihonngo.comm.ArgumentKey;
import com.austin.mynihonngo.comm.FragTAG;
import com.austin.mynihonngo.comm.GlobalParams;
import com.austin.mynihonngo.engine.IFragmentListener;
import com.austin.mynihonngo.engine.OnResultListener;
import com.austin.mynihonngo.model.bean.SakuraResult;
import com.austin.mynihonngo.presentation.ISakuraBunnpoPresenter;
import com.austin.mynihonngo.utils.BeanFactoryUtil;
import com.austin.mynihonngo.utils.UIManager;
import com.austin.mynihonngo.utils.UIUtil;
import com.austin.mynihonngo.viewfeature.IView;

/**
 * Created by com.austin on 2016/6/28.
 * Desc: 文法课时，包括各课(unit)语法
 */
public abstract class BaseWordPagerInnerB implements IView,IFragmentListener {

    View view;
    ISakuraBunnpoPresenter presenter;
    private Context context;

    private Fragment mParentFrag;
    protected static final String TAG = BaseWordPagerInnerB.class.getSimpleName();
    /**
     * 上拉加载，下拉刷新组件
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private RecyclerView mRecyclerView;
    private Integer level;

    /**
     * 当前展示的子页数据类型，如在名词界面时，代表 动物、植物等分类请求参数id
     */
    private int mUnit;//代表sakura词汇中心某个级别下的课时
    private SkrRecyclerViewAdapterB adapter;

//    boolean isLoading;
    private FragmentManager mFragManager;

    public BaseWordPagerInnerB(Context context, int mUnit, Integer level) {
        this.mUnit = mUnit;
        this.context = context;
        this.level = level;
        GlobalParams.iFragmentListenerSakuraGrammar=this;
        mFragManager = ((MainActivity)context).getSupportFragmentManager();
        initPresenter();
        bindView();
        configView();
    }

    private void initPresenter() {
        this.presenter = BeanFactoryUtil.getImpl(ISakuraBunnpoPresenter.class);
        if (presenter == null) {
            throw new NullPointerException("check if you forget to write ISakuraBunnpoPresenter's implement class in properties file ?");
        }
        presenter.init(this);
    }

    public void bindView() {
        adapter = new SkrRecyclerViewAdapterB(context, presenter.getSkrAdapterData());
        view = View.inflate(context, R.layout.wordpager, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    public void configView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ctrlToolBarShowOrHide(recyclerView,dx,dy);
            }
        });
        //添加点击事件
        adapter.setOnItemClickListener(new SkrRecyclerViewAdapterB.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GlobalParams.globalWordAdapterB = adapter;
//                Log.d(TAG, "item position = " + position);
                if(adapter.getItemViewType(position)!=WordRecyclerViewAdapter.TYPE_ITEM)return;
                SakuraResult sentence = adapter.getData().get(position-1);
                SakuraSentceDetailFrg target = (SakuraSentceDetailFrg) mFragManager.findFragmentByTag(FragTAG.FRAG_TAG_SENTENCE_DETAIL);
                if(target == null){
                    target = new SakuraSentceDetailFrg();
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(ArgumentKey.SentenceArguBundleKey,sentence);
                bundle.putInt(ArgumentKey.position,position-1);
                UIManager.getInstance().changeFragmentAndSaveViews2(mParentFrag,target,true,bundle, FragTAG.FRAG_TAG_SENTENCE_DETAIL);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d(TAG, "LongItemClick item position = " + position);
            }
        });
    }

    //控制首页中ToolBar的显示和隐藏
    public abstract void ctrlToolBarShowOrHide(RecyclerView recyclerView, int dx, int dy);

    private void refresh() {
        presenter.getClassifyItemData(level,mUnit,new OnResultListener() {
            @Override
            public void onGetData(Object obj, int what) {
                {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg, int what) {
                dismisProgress();
                UIUtil.showToastSafe(msg);
            }
        });
    }

    public View getRootView() {
        return view;
    }

    @Override
    public void dismisProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        View lastV = layoutManager.findViewByPosition(layoutManager.findLastVisibleItemPosition());
        if (lastV != null) {
            Log.e(TAG + "lastView !=null", lastV.toString());
            if (lastV instanceof LinearLayout) {
                adapter.notifyItemRemoved(adapter.getItemCount());
            }
        } else {
            Log.e(TAG + "lastView == null", "lastView ==null");
        }
    }

    public void initBaseData() {
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, UIUtil.dip2px(44));
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();
    }

    //mParentFrag 是作为frag跳转时指定跳转前的页面
    public void setParentFrag(BaseWordPagerB parentFrag) {
        this.mParentFrag = parentFrag;
    }

    @Override
    public void openNextWordDetailFrg(int currtPosition) {
        if(currtPosition >= GlobalParams.globalWordAdapterB.getData().size()-1){
            UIUtil.showToastSafe("没了");
            return;
        }
        SakuraResult word = GlobalParams.globalWordAdapterB.getData().get(currtPosition + 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ArgumentKey.SentenceArguBundleKey,word);
        bundle.putInt(ArgumentKey.position,currtPosition+1);
        SakuraSentceDetailFrg target = (SakuraSentceDetailFrg) mFragManager.findFragmentByTag(FragTAG.FRAG_TAG_SENTENCE_DETAIL);
        if(target == null){
            target = new SakuraSentceDetailFrg();
            UIManager.getInstance().changeFragmentAndSaveViews2(mParentFrag,target,true,bundle, FragTAG.FRAG_TAG_SENTENCE_DETAIL);
        }else{
            target.refreshUIAdv(currtPosition+1,word);
        }
    }

    @Override
    public void openPreWordDetailFrg(int currtPosition) {
        if(currtPosition <= 0){
            UIUtil.showToastSafe("没了");
            return;
        }
        SakuraResult sakura = GlobalParams.globalWordAdapterB.getData().get(currtPosition - 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ArgumentKey.SentenceArguBundleKey,sakura);
        bundle.putInt(ArgumentKey.position,currtPosition-1);
        SakuraSentceDetailFrg target = (SakuraSentceDetailFrg) mFragManager.findFragmentByTag(FragTAG.FRAG_TAG_SENTENCE_DETAIL);
        if(target == null){
            target = new SakuraSentceDetailFrg();
            UIManager.getInstance().changeFragmentAndSaveViews2(mParentFrag,target,true,bundle, FragTAG.FRAG_TAG_SENTENCE_DETAIL);
        }else{
            target.refreshUIAdv(currtPosition-1,sakura);
        }
    }
}
