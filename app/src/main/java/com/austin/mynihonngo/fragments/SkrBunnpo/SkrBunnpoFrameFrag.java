package com.austin.mynihonngo.fragments.SkrBunnpo;

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
import com.austin.mynihonngo.utils.UIUtil;

/**
 * Created by com.austin on 2016/6/28.
 * Desc: Sakura 文法框架Fragment
 */
public class SkrBunnpoFrameFrag extends Fragment{

    private String TAG = SkrBunnpoFrameFrag.class.getSimpleName();
    private int currSideMenuPosition = 0;//记录最新的当前选中的侧边栏的角标，默认0
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_bunnpo,container,false);
        return view;
    }

     /**
     * 根据侧边栏的角标来更换内容区域要显示的frag
     * @param sidePostion 侧边栏角标
     */
    public void replaceContentViewBySidePosition(int sidePostion){
        Fragment target ;
        target = new SakuraBunnpoFrag();
        Bundle bundle = new Bundle();
        bundle.putStringArray(ArgumentKey.TitleArguBundle, UIUtil.getContext().getResources().getStringArray(R.array.sakura_unit));
        currSideMenuPosition = sidePostion;
        switch (sidePostion){
            case 0://2级别
                bundle.putInt(ArgumentKey.SkrWordLevel,ConstantValue.LEVEL2);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content3,false,bundle, FragTAG.Frag_TAG_LEVEL2);
                break;
            case 1://3级别
                bundle.putInt(ArgumentKey.SkrWordLevel,ConstantValue.LEVEL3);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content3,false,bundle, FragTAG.Frag_TAG_LEVEL3);
                break;
            case 2://4级别
                bundle.putInt(ArgumentKey.SkrWordLevel,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content3,false,bundle, FragTAG.Frag_TAG_LEVEL4);
                break;
            case 3://5级别
                bundle.putInt(ArgumentKey.SkrWordLevel,ConstantValue.LEVEL5);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content3,false,bundle, FragTAG.Frag_TAG_LEVEL5);
                break;
            case 4://6级别
                bundle.putInt(ArgumentKey.SkrWordLevel,ConstantValue.LEVEL6);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content3,false,bundle, FragTAG.Frag_TAG_LEVEL6);
                break;
        }
    }

    public int getCurrSlidePosition() {
        return currSideMenuPosition;
    }
}
