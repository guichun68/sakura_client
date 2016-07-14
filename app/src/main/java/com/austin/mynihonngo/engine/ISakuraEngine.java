package com.austin.mynihonngo.engine;

/**樱花JP 网络引擎
 * @author Austin
 *
 */
public interface ISakuraEngine {
	/**获得樱花 JP的侧边栏数据（学习级别）
	 * @param listener
	 * @param flag
	 */
	void getSakuraData(OnResultListener listener, Boolean flag,int what);
	
	void getSakuraWordCenterData(OnResultListener listener, Boolean flag,int what);

}
