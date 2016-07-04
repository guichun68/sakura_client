package austin.mysakuraapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import austin.mysakuraapp.R;

/**
 * Created by austin on 2016/6/28.
 * Desc: Sakura 单词
 */
public class SkrTanngoFrag extends Fragment{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_skrtanngo,container,false);
        return view;
    }
}
