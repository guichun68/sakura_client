package austin.mysakuraapp.comm;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import austin.mysakuraapp.adapters.WordRecyclerViewAdapter;
import austin.mysakuraapp.engine.IFragmentListener;
import austin.mysakuraapp.engine.TabChangedListener;
import austin.mysakuraapp.fragments.SkrBunnpo.SkrRecyclerViewAdapterB;
import austin.mysakuraapp.fragments.skrTanngo.WordRecyclerViewAdapterS;
import austin.mysakuraapp.utils.BeanFactoryUtil;


public class GlobalParams {
	public static String BASE_URL ;
	//记录最后一次离开樱花单词页(SkrTanngoFrag)面时侧边栏选中的角标,下面几个变量同样的道理
	public static int skrTanngoSidePosition;
	public static int tanngoFragSidePostion;
	public static int skrBunnpoSidePosition;

	static{
		BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_fjjsp");
	}
	/**
	 * 获取名词 的RootUrl
	 */
	public static String URL_NOUNS = GlobalParams.BASE_URL+"/word/getNounServlet";
	public static String URL_SAKURA_WORD_CENTER = GlobalParams.BASE_URL+"/word/getSakuraWordCenter";
	
	public static String URL_SAKURA_CLASS_ITEM = GlobalParams.BASE_URL+"/sakura/getSakuraServlet";
	public static String URL_VERBS = GlobalParams.BASE_URL+"/word/getVerbServlet";
	public static String URL_ADJS = GlobalParams.BASE_URL+"/word/getAdjServlet";
	public static String URL_OTHER = GlobalParams.BASE_URL+"/word/getOtherServlet";
	
	public static String URL_SAKURA_WORD = GlobalParams.BASE_URL+"/word/getSakuraWord";
	
	
	
	
	public static String URL_LOGIN = GlobalParams.BASE_URL+"/user/LoginServletApp";
	public static String URL_UPDATE = GlobalParams.BASE_URL+"/app/updateApp";
	public static String URL_GET_GRAMMARS = GlobalParams.BASE_URL+"/sakura/getGrammars";
	public static String URL_REGIST = GlobalParams.BASE_URL+"/user/RegistServletApp";
	public static String URL_NULL = GlobalParams.BASE_URL+"";
	/**
	 * 单词中心 侧边栏请求 url
	 */
	public static String URL_SLIDING_MENU_WORD_CENTER = GlobalParams.BASE_URL+"/word/wordCenterServletApp";
	public static String URL_SLIDING_MENU_SAKURA = GlobalParams.BASE_URL+"/sakura/getLevels";
	
	public static void refreshIP(){
		URL_SLIDING_MENU_WORD_CENTER = GlobalParams.BASE_URL+"/word/wordCenterServletApp";
		URL_SLIDING_MENU_SAKURA = GlobalParams.BASE_URL+"/sakura/getLevels";
		URL_SAKURA_CLASS_ITEM = GlobalParams.BASE_URL+"/sakura/getSakuraServlet";
		URL_SAKURA_WORD_CENTER= GlobalParams.BASE_URL+"/word/getSakuraWordCenter";
		URL_SAKURA_WORD = GlobalParams.BASE_URL+"/word/getSakuraWord";
		URL_NOUNS = GlobalParams.BASE_URL+"/word/getNounServlet";
		URL_VERBS = GlobalParams.BASE_URL+"/word/getVerbServlet";
		URL_ADJS = GlobalParams.BASE_URL+"/word/getAdjServlet";
		URL_OTHER = GlobalParams.BASE_URL+"/word/getOtherServlet";
		
		URL_LOGIN = GlobalParams.BASE_URL+"/user/LoginServletApp";
		URL_REGIST = GlobalParams.BASE_URL+"/user/RegistServletApp";
		URL_NULL = GlobalParams.BASE_URL+"";
		URL_UPDATE = GlobalParams.BASE_URL+"/app/updateApp";
		URL_GET_GRAMMARS = GlobalParams.BASE_URL+"/sakura/getGrammars";

	}
	public static boolean isFirstComeInSkrTanngo = true;//是否是第一次进入sakura单词页
	public static boolean isFirstComeInSkrBunnpo = true;//是否是第一次进入sakura 语法页
	public static boolean isLoggedIn;
	public static AppCompatActivity MAIN;
	/**
	 * 代理ip
	 */
	public static String PROXY_IP = "";
	/**
	 * 代理端口
	 */
	public static Integer PROXY_PORT = 0;
	public static int default_layout_id = 0;
	/**
	 * 当前选中的选项卡 角标（比如App打开时默认0,即表示打开了单词中心）
	 */
	public static int CURR_TAB_INDEX;
	
//	public static TabChangedListener tabChangedListener;
	public static IFragmentListener iWord;
	public static IFragmentListener iFragmentListenerSakuraGrammar;
	public static boolean isCheckedUpdate = false;// 是否已经运行检查更新服务了
	public static boolean showNihonngo = false;//sakura语法页显示日文？默认显示中文。
	public static TabChangedListener mTabChangedListener;//BaseItemPager类使用的tab改变监听器
	public static TabChangedListener mTabChangedLisBaseItemPager;
	//记录顶部TabLayout的高度，当实际TabLayout绘制完成，有高度数值时回调并将本变量赋值；本变量在所有带tabLayout控件的frag中共用
	public static Integer TAB_LAYOUT_HEIGHT;
	public static Fragment foreFrag;
	/**
	 * 缓存单词中心的adapter
	 */
	public static WordRecyclerViewAdapter globalWordAdapter;
	/**
	 * 樱花日语单词页的adapter
	 */
	public static WordRecyclerViewAdapterS globalWordAdapterS;
	/**
	 * 樱花语法页的adapter
	 */
	public static SkrRecyclerViewAdapterB globalWordAdapterB;
	public static boolean isDrawerOpened ;
}
