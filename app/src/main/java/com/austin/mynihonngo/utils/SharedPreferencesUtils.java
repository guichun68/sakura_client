package com.austin.mynihonngo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.austin.mynihonngo.fragments.skrTanngo.WordRecyclerViewAdapterS;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

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


	/**(业务相关的方法)
	 * 保存樱花 单词列表的adapter到SharePreference
	 * @param adapter
	 */
	public static void saveAdapterSToSP(Context ctx,WordRecyclerViewAdapterS adapter){
		SharedPreferences preferences =ctx.getSharedPreferences("base64",Context.MODE_PRIVATE);
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(adapter);
			// 将字节流编码成base64的字符窜
			String oAuth_Base64 = new String(Base64.encodeBase64(baos
					.toByteArray()));
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("oAuth_1", oAuth_Base64);

			editor.apply();
		} catch (IOException e) {
			// TODO Auto-generated
		}
//		Log.i("ok", "存储成功");
	}

	/**(业务相关的方法)
	 * 从SharePreference读取樱花 单词列表的adapter
	 * @return
	 */
	public static WordRecyclerViewAdapterS readSakuraWordAdapter(Context ctx) {
		WordRecyclerViewAdapterS adapter = null;
		SharedPreferences preferences = ctx.getSharedPreferences("base64",
				Context.MODE_PRIVATE);
		String productBase64 = preferences.getString("oAuth_1", "");

		//读取字节
		byte[] base64 = Base64.decodeBase64(productBase64.getBytes());

		//封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			//再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			try {
				//读取对象
				adapter = (WordRecyclerViewAdapterS) bis.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adapter;
	}
}
