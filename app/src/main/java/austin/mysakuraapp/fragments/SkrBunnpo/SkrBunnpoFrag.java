package austin.mysakuraapp.fragments.SkrBunnpo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ArgumentKey;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.FragTAG;
import austin.mysakuraapp.fragments.skrTanngo.BaseWordPagerS;
import austin.mysakuraapp.utils.UIManager;
import austin.mysakuraapp.utils.UIUtil;

/**
 * Created by austin on 2016/6/28.
 * Desc: Sakura 文法
 */
public class SkrBunnpoFrag extends Fragment{

    private String TAG = SkrBunnpoFrag.class.getSimpleName();
    private int currSideMenuPosition = 0;//记录最新的当前选中的侧边栏的角标，默认0
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_bunnpo,container,false);
        return view;
    }

/*    *//**
     * 根据侧边栏的角标来更换内容区域要显示的frag
     * @param sidePostion 侧边栏角标
     *//*
    public void replaceContentViewBySidePosition(int sidePostion){
        Fragment target ;
        target = new BaseWordPagerS();
        Bundle bundle = new Bundle();
        bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.sakura_unit));
        currSideMenuPosition = sidePostion;
        switch (sidePostion){
            case 0://1级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL1);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL1);
                break;
            case 1://2级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL2);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL2);
                break;
            case 2://3级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL3);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL3);
                break;
            case 3://4级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL4);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL4);
                break;
            case 4://5级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL5);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL5);
                break;
            case 5://6级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL6);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL6);
                break;
            case 6://7级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL7);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL7);
                break;
            case 7://8级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL8);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL8);
                break;
            case 8://9级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL9);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL9);
                break;
            case 9://10级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL10);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL10);
                break;
            case 10://11级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL11);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL11);
                break;
            case 11://12级别
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.LEVEL12);
                UIManager.getInstance().changeFragmentWithTag(target,R.id.fl_content2,false,bundle, FragTAG.Frag_TAG_LEVEL12);
                break;

        }
    }
*/
    public int getCurrSlidePosition() {
        return currSideMenuPosition;
    }
}
