package austin.mysakuraapp.comm;

/**
 * Created by austin on 2016/7/8.
 * Desc: 专门存放frag之间传递参数的Bundle对象传递参数时的key的名字
 */
public interface ArgumentKey {
    //单词中心页面点击侧边栏打开相关类型（如名词、形容词..）frag页面时传递的顶部title集合的Bundle key
    String TitleArguBundle = "tab_titles";
    String WordArguBundleKey = "WordArguBundleKey";
    String position = "position";
}
