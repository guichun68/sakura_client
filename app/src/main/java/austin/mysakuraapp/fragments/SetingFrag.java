package austin.mysakuraapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import austin.mysakuraapp.R;

/**
 * Created by austin on 2016/6/28.
 * Desc: Sakura 单词
 */
public class SetingFrag extends Fragment{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_seting,container,false);
        return view;
    }
}
