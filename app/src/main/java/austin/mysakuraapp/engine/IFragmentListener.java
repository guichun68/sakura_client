package austin.mysakuraapp.engine;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import java.util.ArrayList;

public interface IFragmentListener {
	
/*
	*/
/**打开指定fragment
	 * @param fragTag 指定要打开的fragment的tag标识，该标识从GlobalParams中的FRAG_TAG_XXX取值
	 * @param param 要传递的参数
	 *//*

	void openFragment(String fragTag, Integer... param);
	
	*/
/**打开指定fragment
	 * @param hidTag 要隐藏的frag
	 * @param fragTag 指定要打开的fragment的tag标识，该标识从GlobalParams中的FRAG_TAG_XXX取值
	 * @param bundle 传递的bundle类型值
	 *//*

	void openFragment(Fragment hidTag, String fragTag, Bundle bundle);
	
	*/
/**打开指定fragment
	 * @param hidFrg 要隐藏的fragment
	 * @param list 传递的list值
	 *//*

	<T>void  openFragment(Fragment hidFrg, String fragTag, ArrayList<T> list);

	void openFragment(String fragTag, Bundle bundle);
*/

	/**跳转到下一条数据
	 * @param currtPosition 当前word所在list的角标（位置）
	 */
	void openNextWordDetailFrg(int currtPosition);
	
	/**跳转到上一条数据
	 * @param currtPosition  当前word所在list的角标（位置）
	 */
	void openPreWordDetailFrg(int currtPosition);

	/**
	 * 清除json缓存
	 */
/*	void clearData();*/
}
