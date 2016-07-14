package com.austin.mynihonngo.fragments.wordcenter;

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
import com.austin.mynihonngo.fragments.WordDetailFragment;
import com.austin.mynihonngo.model.bean.WordResult;
import com.austin.mynihonngo.presentation.INounWordPresenter;
import com.austin.mynihonngo.utils.BeanFactoryUtil;
import com.austin.mynihonngo.utils.UIManager;
import com.austin.mynihonngo.utils.UIUtil;
import com.austin.mynihonngo.viewfeature.IView;

/**
 * Created by com.austin on 2016/6/28.
 * Desc: 名词类别，包括各种名词小类
 */
public abstract class BaseWordPagerInner implements IView,IFragmentListener {

    View view;
    INounWordPresenter presenter;
    private Context context;

    private Fragment mParentFrag;
    protected static final String TAG = BaseWordPagerInner.class.getSimpleName();
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
    private int mClassiNo;//当前页面的类别，动物：1 ；植物2； 交通工具类3，其他4; 或者代表sakura词汇中心某个级别下的课时
    /**
     * 分页，默认第一页
     */
    private Integer mPageNo = 1;
    private WordRecyclerViewAdapter adapter;

    boolean isLoading;
    private FragmentManager mFragManager;

    public BaseWordPagerInner(Context context, int classItemId, Integer level) {
        mClassiNo = classItemId;
        this.context = context;
        this.level = level;
        GlobalParams.iWord = this;
        mFragManager = ((MainActivity)context).getSupportFragmentManager();
        initPresenter();
        bindView();
        configView();
    }

    private void initPresenter() {
        this.presenter = BeanFactoryUtil.getImpl(INounWordPresenter.class);
        if (presenter == null) {
            throw new NullPointerException("check if you forget to write INounWordPresenter's implement class in properties file ?");
        }
        presenter.init(this);
    }

    public void bindView() {
        adapter = new WordRecyclerViewAdapter(context, presenter.getAdapterData());
        view = View.inflate(context, R.layout.wordpager, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    public void configView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        //进入时本页时默认自动刷新

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
//                Log.d("test", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ctrlToolBarShowOrHide(recyclerView,dx,dy);

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
//                    Log.d("test", "loading executed：isLoading=" + isLoading);

                    boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
//                        Log.e(TAG, "加载更多");
                        isLoading = true;
                        presenter.getWordItemData(mClassiNo, level, ++mPageNo, false, new OnResultListener() {

                            @Override
                            public void onGetData(Object obj, int what) {
                                dismisProgress();
                                if (obj == null) {
                                    mPageNo--;
                                    UIUtil.showToastSafe(UIUtil.getString(R.string.no_enough_data));
                                    return;
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(String msg, int what) {
                                dismisProgress();
                                mPageNo--;
                                UIUtil.showToastSafe(msg);
                            }
                        });
                    }
                }
            }
        });
        //添加点击事件
        adapter.setOnItemClickListener(new WordRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GlobalParams.globalWordAdapter = adapter;
//                Log.d(TAG, "item position = " + position);
                if(adapter.getItemViewType(position)!=WordRecyclerViewAdapter.TYPE_ITEM)return;
                WordResult word = adapter.getData().get(position-1);
                WordDetailFragment target = (WordDetailFragment) mFragManager.findFragmentByTag(FragTAG.FRAG_TAG_WORD_DETAIL);
                if(target == null){
                    target = new WordDetailFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(ArgumentKey.WordArguBundleKey,word);
                bundle.putInt(ArgumentKey.position,position-1);
                UIManager.getInstance().changeFragmentAndSaveViews2(mParentFrag,target,true,bundle, FragTAG.FRAG_TAG_WORD_DETAIL);
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
        mPageNo = 1;
        presenter.getWordItemData(mClassiNo, level, mPageNo, true, new OnResultListener() {
            @Override
            public void onGetData(Object obj, int what) {
                {
                    dismisProgress();
                    if (obj == null) {
                        UIUtil.showToastSafe(UIUtil.getString(R.string.no_enough_data));
                        return;
                    }
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
        isLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        View lastV = layoutManager.findViewByPosition(layoutManager.findLastVisibleItemPosition());
        if (lastV != null) {
            Log.e(TAG + "lastView !=null", lastV.toString());
            if (lastV instanceof LinearLayout) {
                Log.e(TAG+"comin","in if");
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

    public void setParentFrag(BaseWordPager parentFrag) {
        this.mParentFrag = parentFrag;
    }

    @Override
    public void openNextWordDetailFrg(int currtPosition) {
        if(currtPosition >= GlobalParams.globalWordAdapter.getData().size()-1){
            UIUtil.showToastSafe("没了");
            return;
        }
        WordResult word = GlobalParams.globalWordAdapter.getData().get(currtPosition + 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ArgumentKey.WordArguBundleKey,word);
        bundle.putInt(ArgumentKey.position,currtPosition+1);
        WordDetailFragment target = (WordDetailFragment) mFragManager.findFragmentByTag(FragTAG.FRAG_TAG_WORD_DETAIL);
        if(target == null){
            target = new WordDetailFragment();
            UIManager.getInstance().changeFragmentAndSaveViews2(mParentFrag,target,true,bundle, FragTAG.FRAG_TAG_WORD_DETAIL);
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
        WordResult word = GlobalParams.globalWordAdapter.getData().get(currtPosition - 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ArgumentKey.WordArguBundleKey,word);
        bundle.putInt(ArgumentKey.position,currtPosition-1);
        WordDetailFragment target = (WordDetailFragment) mFragManager.findFragmentByTag(FragTAG.FRAG_TAG_WORD_DETAIL);
        if(target == null){
            target = new WordDetailFragment();
            UIManager.getInstance().changeFragmentAndSaveViews2(mParentFrag,target,true,bundle, FragTAG.FRAG_TAG_WORD_DETAIL);
        }else{
            target.refreshUIAdv(currtPosition-1,word);
        }
    }
}
