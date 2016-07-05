package austin.mysakuraapp.engine;

import java.util.HashMap;


/**单词中心 网络引擎
 * @author Austin
 *
 */
public interface IWordCenterEngine {
	/**获得单词中心的基本数据
	 * @param listener
	 * @param flag
	 */
	void getWordCenterData(OnResultListener listener, Boolean flag,int what);
	

	/**抽出的通用方法，获得 词汇中心--》某一个大类下(如形容词)--》某一个单独类别的数据（如一类形容词）
	 * @param wordFlag 值取自GlobalParams.URL_LOGIN_...用来告诉接口你要获得那种词性的单词
	 * @param param
	 * @param onResultListener
	 * @param b
	 */
	void getClassifyItemData(String wordFlag, HashMap<String, String> param,
							 OnResultListener onResultListener, boolean b,int what);
}
