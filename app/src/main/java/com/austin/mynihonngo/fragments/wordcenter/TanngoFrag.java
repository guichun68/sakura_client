package com.austin.mynihonngo.fragments.wordcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.austin.mynihonngo.R;
import com.austin.mynihonngo.comm.ArgumentKey;
import com.austin.mynihonngo.comm.ConstantValue;
import com.austin.mynihonngo.comm.FragTAG;
import com.austin.mynihonngo.utils.UIManager;

/**
 * Created by com.austin on 2016/6/28.
 * Desc: 单词中心Fragment
 */
public class TanngoFrag extends Fragment {

    private View view;
    private int mCurrSidePosition = 0;//记录最新的当前选中的侧边栏的角标，默认0

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag,container,false);
        return view;
    }

    /**
     * 根据侧边栏的角标来更换内容区域要显示的frag
     * @param sidePostion 侧边栏角标
     */
    public void replaceContentViewBySidePosition(int sidePostion){
        Fragment target = null;
        target = new BaseWordPager();
        Bundle bundle = new Bundle();
        mCurrSidePosition = sidePostion;
        switch (sidePostion){
            case 0://名词
                String[] stringArray = {"动物","植物","交通","其他"};
                bundle.putStringArray(ArgumentKey.TitleArguBundle,stringArray);
                bundle.putInt(ArgumentKey.WordCenterType,ConstantValue.WordTypeNoun);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content,false,bundle, FragTAG.FRAG_TAG_NOUN);
                break;
            case 1://动词
                bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.verb_tab_title));
                bundle.putInt(ArgumentKey.WordCenterType,ConstantValue.WordTypeVerb);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content,false,bundle, FragTAG.FRAG_TAG_VERB);
                break;
            case 2://形容词
                bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.adj_tab_title));
                bundle.putInt(ArgumentKey.WordCenterType,ConstantValue.WordTypeAdj);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content,false,bundle, FragTAG.FRAG_TAG_ADJ);
                break;
            case 3://其他
                bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.other_tab_title));
                bundle.putInt(ArgumentKey.WordCenterType,ConstantValue.WordTypeOther);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content,false,bundle, FragTAG.FRAG_TAG_OTHER);
                break;

        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        replaceContentViewBySidePosition(0);
    }

    public int getCurrSlidePosition() {
        return mCurrSidePosition;
    }
}