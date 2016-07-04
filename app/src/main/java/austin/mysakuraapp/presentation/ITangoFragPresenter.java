package austin.mysakuraapp.presentation;

import android.content.Context;

import java.util.List;

import austin.mysakuraapp.fragments.wordcenter.BaseWordPager;
import austin.mysakuraapp.fragments.wordcenter.TangoFrag;
import austin.mysakuraapp.model.TanngoType;
import austin.mysakuraapp.viewfeature.ITangoFragView;

/**
 * Created by austin on 2016/7/1.
 * Desc:
 */
public interface ITangoFragPresenter {
    /**
     * 初始化指定单词类型的view集合以放到相应的viewPager中
     * @param type 指定要获取的view的类型
     */
    void initSpecifyTypeViews(TanngoType type);

    void onCreate(Context view);

    /**
     * 得到TabLayout的标题 （数组）
     * @return
     */
    String[] getTabLayoutTitles();

    List<BaseWordPager> getPagers();
}
