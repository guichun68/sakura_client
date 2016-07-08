package austin.mysakuraapp.fragments.wordcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ArgumentKey;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.utils.UIManager;

/**
 * Created by austin on 2016/6/28.
 * Desc: 单词中心之形容词Fragment
 */
public class TanngoFrag extends Fragment {

    private View view;
    private FragmentManager mFragManager;

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
        switch (sidePostion){
            case 0://名词
                String[] stringArray = {"动物","植物","交通","其他"};
                bundle.putStringArray(ArgumentKey.TitleArguBundle,stringArray);
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.WordTypeNoun);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_NOUN);
                break;
            case 1://动词
                bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.verb_tab_title));
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.WordTypeVerb);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_VERB);
                break;
            case 2://形容词
                bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.adj_tab_title));
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.WordTypeAdj);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_ADJ);
                break;
            case 3://其他
                bundle.putStringArray(ArgumentKey.TitleArguBundle,getResources().getStringArray(R.array.other_tab_title));
                bundle.putInt(ConstantValue.WordCenterType,ConstantValue.WordTypeOther);
                UIManager.getInstance().changeFragmentWithTag(target,false,bundle, ConstantValue.FRAG_TAG_OTHER);
                break;

        }
    }

    public void setFragmentManager(FragmentManager manager){
        this.mFragManager = manager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        replaceContentViewBySidePosition(0);
    }
}