package austin.mysakuraapp.fragments.SkrBunnpo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import austin.mysakuraapp.R;

/**
 * Created by austin on 2016/6/28.
 * Desc: Sakura 文法
 */
public class SkrBunnpoFrag extends Fragment{
    private View view;
    private int currSideMenuPosition = 0;//记录最新的当前选中的侧边栏的角标，默认0
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_bunnpo,container,false);
        return view;
    }

    public int getCurrSlidePosition() {
        return currSideMenuPosition;
    }
}
