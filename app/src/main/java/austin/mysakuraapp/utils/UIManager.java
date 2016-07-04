package austin.mysakuraapp.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.GlobalParams;


/**
 * 中间容器的管理工具
 */
public class UIManager {
	private static UIManager instance = new UIManager();

	public static UIManager getInstance() {
		return instance;
	}

	private UIManager() {
	}


/*

	public void changeFragmentWithTag(Fragment target, boolean isAddStack,
									  Bundle bundle, String fragTag) {
		FragmentManager manager = GlobalParams.MAIN.getSupportFragmentManager();
		if (target.isVisible()) {
			return;
		}
		if (manager.getBackStackEntryCount() > 0) {
			manager.popBackStack();
		}
		if (target.isAdded()) {
			Fragment fragment = manager.findFragmentByTag(fragTag);
			manager.beginTransaction().show(fragment).commit();
			return;
		}
		if (bundle != null) {
			target.setArguments(bundle);
		}
		FragmentTransaction transaction = manager.beginTransaction();

		// 返回键操作
		if (isAddStack) {
			transaction.addToBackStack(null);
		}

		transaction.replace(R.id.content_frame, target, fragTag);

		transaction.commitAllowingStateLoss();

	}
*/


	/**
	 * 界面切换--保留切换前fragment的页面视图到回退栈
	 * 
	 * @param hideFragment
	 *            被隐藏掉的fragment
	 * @param target
	 *            目标fragment
	 * @param isAddStack
	 *            是否将hideFragment添加到回退栈
	 * @param bundle
	 *            传递数据用的对象
	 * @param fragmentTag
	 *            target的标签
	 */
/*
	public void changeFragmentAndSaveViews(Fragment hideFragment,
										   Fragment target, boolean showToggle, boolean isAddStack,
										   Bundle bundle, String fragmentTag) {
		if (target.isAdded()) {

			if (GlobalParams.MAIN.getSlidingMenu().isShown() && showToggle) {
				GlobalParams.MAIN.getSlidingMenu().toggle(true);
			}
			// Fragment target2 =
			// GlobalParams.MAIN.getSupportFragmentManager().findFragmentByTag(fragmentTag);
			return;
		}
		if (bundle != null) {
			target.setArguments(bundle);
		}
		FragmentManager manager = GlobalParams.MAIN.getSupportFragmentManager();
		FragmentTransaction tc = manager.beginTransaction();
		tc.setCustomAnimations(R.anim.push_left_out, R.anim.push_right_in,
				R.anim.push_left_in, R.anim.push_right_out);
		// tc.setCustomAnimations(R.anim.push_left_out, R.anim.push_right_in);
		if ( hideFragment != null && !target.isAdded())
			tc.hide(hideFragment);
		tc.add(R.id.content_frame, target, fragmentTag);
		// 是否添加到回退栈，以便按返回键时可以返回hideFragment
		if (isAddStack) {
			tc.addToBackStack(null);
		}
		tc.commitAllowingStateLoss();
	}
*/

	/**
	 * 清空栈
	 */
	public void clearStack() {
		FragmentManager manager = GlobalParams.MAIN.getSupportFragmentManager();
		while (manager.getBackStackEntryCount() > 0) {
			manager.popBackStackImmediate();
		}
	}

	/**
	 * 弹栈操作
	 * 
	 * @param count
	 *            调用的次数
	 */
	public void popBackStack(int count) {
		FragmentManager manager = GlobalParams.MAIN.getSupportFragmentManager();
		for (int i = 0; i < count; i++) {
			manager.popBackStack();
		}
	}
}
