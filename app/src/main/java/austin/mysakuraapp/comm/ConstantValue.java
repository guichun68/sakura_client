package austin.mysakuraapp.comm;


public interface ConstantValue {
	/**
	 * 服务器地址
	 */
//	String MYWEB_URI = "http://tfgyc60474.bj.jspee.com";
//	String MYWEB_URI = "http://192.168.6.103";
//	String MYWEB_URI = "http://192.168.0.1";
//	public final static String BASE_URL = "http://192.168.6.103";
	/**
	 * 登陆类型:本地自动登陆
	 */
	  String LOGIN_TYPE_LOCAL = "LOCAL";
	  String LOGIN_TYPE_REMOTE = "REMOTE";
	/**
	 * Fragment tags
	 */
	  String FRAG_TAG_HOME = "HOME_FRAG";
	  String FRAG_TAG_WORD_DETAIL = "WORD_DETAIL";
	  String FRAG_TAG_MENU = "MENU";
	  String FRAG_TAG_LOGIN = "LOGIN_FRAG";
	  String FRAG_TAG_REGIST = "LOGIN_REGIST";
	/**
	 * 语法详情web页面TAG
	 */
	  String FRAG_TAG_GRAMMAR="FRAG_TAG_GRAMMAR";




	/**
	 * 点击个人中心的“语法和文化”进入后的页面TAG
	 */
	String FRAG_TAG_GRAMMAR_LIST = "Frag_tag_grammar_list";
	/**
	 * 返回值 1 标识访问成功并信息验证通过
	 */
	  Integer ACCCESS_SUCCESS = 1;
	/**
	 * 网络访问成功，但信息验证未通过
	 */
	  Integer ACCCESS_REFUSE = 400;
	
	
	/**
	 * 名词分类之 动物篇
	 */
	  int NOUN_TYPE_ANIMAL = 1;
	  int SAKURA_WORD_TYPE = 312;
	  int LEVEL1 = 1;
	  int LEVEL2 = 2;
	  int LEVEL3 = 3;
	  int LEVEL4 = 4;
	  int LEVEL5 = 5;
	  int LEVEL6 = 6;
	  int LEVEL7 = 7;
	  int LEVEL8 = 8;
	  int LEVEL9 = 9;
	  int LEVEL10 = 10;
	  int LEVEL11 = 11;
	  int LEVEL12 = 12;
	
	  int UNIT1 = 1;
	  int UNIT2 = 2;
	  int UNIT3 = 3;
	  int UNIT4 = 4;
	  int UNIT5 = 5;
	  int UNIT6 = 6;
	  int UNIT7 = 7;
	  int UNIT8 = 8;
	  int UNIT9 = 9;
	  int UNIT10 = 10;
	  int UNIT11 = 11;
	  int UNIT12 = 12;
	
	/**
	 * 名词分类之 植物篇
	 */
	  int NOUN_TYPE_PLANT = 2;
	/**
	 * 名词分类之 交通工具篇
	 */
	  int NOUN_TYPE_VEHICLE = 3;
	/**
	 * 名词分类之 交通工具篇
	 */
	  int NOUN_TYPE_OTHER = 10;
	/**
	 * 动词分类之 1类
	 */
	  int VERB_TYPE_ONE = 11;
	
	/**
	 * 动词分类之 2类
	 */
	  int VERB_TYPE_TWO = 12;
	
	/**
	 * 动词分类之 3类
	 */
	  int VERB_TYPE_THREE = 13;
	
	/**
	 * 1类形容词 い型
	 */
	  int ADJ_TYPE_ONE = 21;
	/**
	 * 2类形容词 な型
	 */
	  int ADJ_TYPE_TWO = 22;
	/**
	 * 其他类型所有标识
	 */
	  int OTHER_TYPE_ALL = 33;
	/**
	 * 接头词
	 */
	  int OTHER_TYPE_ONE = 31;
	/**
	 * 接尾词
	 */
	  int OTHER_TYPE_TWO = 32;
	
	  String FRAG_TAG_SENTENCE_DETAIL = "sakura_sentence_frg";
	
	/**
	 * 保存有wordCenter选项卡中侧边栏条目的集合的key（存储在SharedPreference）
	 */
	  String SP_SLIDINGM_LISTSTR_WORD_CENTER="SP_SLIDINGM_LISTSTR_WORD_CENTER";
	/**
	 * 保存有樱花JP选项卡中侧边栏条目的集合的key（存储在SharedPreference）
	 */
	  String SP_SLIDINGM_LISTSTR_SKR="SP_SLIDINGM_LISTSTR_skr";
	/**
	 * 保存有wordCenter选项卡中侧边栏条目的当前选中角标的key（存储在SharedPreference）
	 */
	  String SP_SLIDINGM_CURRINDEX_WORD_CENTER = "sp_slidingM_liststr";
	/**
	 * 保存有樱花JP选项卡中侧边栏条目的当前选中条目的角标的key（存储在SharedPreference）
	 */
	  String SP_SLIDINGM_CURRINDEX_SAKURA = "sp_slidingM_currIndex_skr_liststr";
	  String SP_SLIDINGM_CURRINDEX_SAKURA_WD = "sp_slidingM_currIndex_skr_liststrWD";
	
	  String SP_SLIDINGM_LISTSTR_SAKURA_WD = "SP_SLIDINGM_LISTSTR_SAKURA_WD";
	  int FAILURE = 0x131FF;

	int WHAT_UPDATE = 666;
	int WHAT_GET_GRAMMAR = 777;
	int WHAT_BASE = 888;

	String WordCenterType = "word_center_type";
	int WordTypeNoun = 0,WordTypeVerb=1,WordTypeAdj=2,WordTypeOther=3;

}
