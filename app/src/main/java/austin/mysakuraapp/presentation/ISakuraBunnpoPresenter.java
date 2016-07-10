package austin.mysakuraapp.presentation;

import java.util.List;

import austin.mysakuraapp.engine.OnResultListener;
import austin.mysakuraapp.model.bean.SakuraResult;
import austin.mysakuraapp.viewfeature.IView;

/**
 * Created by austin on 2016/7/10.
 * Desc: 樱花日语文法 页presenter
 */
public interface ISakuraBunnpoPresenter {
    void init(IView view);

    /**
     * 获取语法页面填充RecyclerView的数据
     * @return
     */
     List<SakuraResult> getSkrAdapterData();

    /**抽出的通用方法，获得 语法页--》某一个大类下(Level4)--》某一个单独类别(unit)的数据（如unit4）
     * @param level 级别
     * @param unit 单元
     * @param onResultListener 监听
     */
    void getClassifyItemData(int level,int unit,OnResultListener onResultListener);

}
