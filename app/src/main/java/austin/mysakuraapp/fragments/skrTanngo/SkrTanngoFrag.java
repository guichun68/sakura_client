package austin.mysakuraapp.fragments.skrTanngo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ArgumentKey;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.utils.UIManager;

/**
 * Created by austin on 2016/6/28.
 * Desc: 樱花单词中心Fragment
 */
public class SkrTanngoFrag extends Fragment {

    private View view;

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
        Fragment target ;
        target = new BaseWordPagerS();
        Bundle bundle = new Bundle();
        bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.sakura_unit));
        switch (sidePostion){
            case 0://1级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL1);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_NOUN);
                break;
            case 1://2级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL2);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_VERB);
                break;
            case 2://3级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL3);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_ADJ);
                break;
            case 3://4级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 4://5级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 5://6级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 6://7级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 7://8级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 8://9级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 9://10级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 10://11级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;
            case 11://12级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;

        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        replaceContentViewBySidePosition(1);
    }

}