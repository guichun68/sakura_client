package austin.mysakuraapp.viewfeature;

/**
 * Created by austin on 2016/7/6.
 * Desc: 首页View行为
 */
public interface IMainView {

    void showToolBar();

    void hideToolBar();

    /**
     * 得到当前Toolbar的显示装填
     * @return true 显示中;false 已隐藏
     */
    boolean isToobarShow();
}
