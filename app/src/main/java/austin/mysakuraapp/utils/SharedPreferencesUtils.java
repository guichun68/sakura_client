package austin.mysakuraapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {
//	private static SharedPreferences _pushPref = null;
	public static String CONFIG = "config_nihonngo";
	private static SharedPreferences sharedPreferences; 
	private static Editor _edit = null;
	
	/**
	 * // 上次登录的用户名
	 */
	public static String LAST_LOGIN_NAME = "lastLoginName";
	/**
	 * 上次登录的用户名
	 */
	public static final String LAST_LOGIN_ID = "lastLoginID";
	/**
	 * // 自动登录标识
	 */
	public static final String AUTO_LOGIN = "autoLogin";
	/**
	 * 缓存用户密码用的key
	 */
	public static final String USER_PSW = "user_psw";
	
	public static void saveStringData(Context context,String key,String value){
		if(sharedPreferences == null){
			sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putString(key, value).commit();
	}
	
	/**从本地sp获取指定key的值
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getStringData(Context context,String key,String defValue){
		if(sharedPreferences == null){
			sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}
	//------------------------------------------------------
	public static boolean getBoolean(String key) {
		init();
		boolean retBoolean = sharedPreferences.getBoolean(key, false);
		return retBoolean;
	}

	public static void putBoolean(String key, boolean value) {
		_edit.putBoolean(key, value);
		_edit.commit();
	}

	public static String getString(String key, String defValue) {
		init();
		String retStr = sharedPreferences.getString(key, defValue);
		return retStr;
	}

	public static void putString(String key, String value) {
		_edit.putString(key, value);
		_edit.commit();
	}

	public static int getInt(String key, int value) {
		init();
		int retInt = sharedPreferences.getInt(key, value);
		return retInt;
	}

	public static void putInt(String key, int value) {
		_edit.putInt(key, value);
		_edit.commit();
	}
	public static long getLong(String key, long value) {
		init();
		long retIong = sharedPreferences.getLong(key, value);
		return retIong;
	}

	public static void putLong(String key, long value) {
		_edit.putLong(key, value);
		_edit.commit();
	}
	
	private static void init() {
		if (null == sharedPreferences)
			sharedPreferences = UIUtil.getContext().getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);

		if (null == _edit)
			_edit = sharedPreferences.edit();
	}
}
