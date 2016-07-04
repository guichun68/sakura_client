package austin.mysakuraapp.presentation;

import java.util.List;

import austin.mysakuraapp.model.bean.WordResult;
import austin.mysakuraapp.viewfeature.INounWordView;

/**
 * Created by austin on 2016/6/29.
 * Desc: 名词页面行为接口
 */
public interface INounWordPresenter {

    void init(INounWordView view);

    /**
     * 获取单词
     * @param classifyType 对应的侧边栏大类
     * @param level 樱日单词页，代表要获取单词所在的级别，为null，表示非樱日单词
     * @param pageNo 页数
     * @param isRefresh 是否是刷新操作
     */
    void getWordItemData(Integer classifyType,Integer level,Integer pageNo,boolean isRefresh);

    /**
     * 获取填充RecyclerView的数据
     * @return
     */
    List<WordResult> getAdapterData();

    /**
     * 刷新操作的相关逻辑
     */
    void refresh();
}
