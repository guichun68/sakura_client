package com.austin.mynihonngo.engine;



public interface IUserEngine {
	/**
	 * 用户注册的方法
	 * 
	 * @param user
	 *            封装用户信息的bean
	 * @param listener
	 *            回调的接口
	 * @param flag
	 *            是否显示进度框
	 */
/*
	void regist(User user, OnResultListener listener, Boolean flag);
*/

	/**
	 * 用户登录的方法
	 * 
	 * @param user
	 * @param listener
	 * @param flag
	 */
/*
	void login(User user, OnResultListener listener, Boolean flag);
*/


	/**
	 * 修改用户信息,用户信息(userId必有)，其他信息动态，有则修改，无则不改
	 * 
	 * @param params
	 *            User转换成的json字符串
	 * @param listener
	 * @param flag
	 */
	void updateUser(String params, OnResultListener listener, Boolean flag);

	/**
	 * 更改密码
	 * 
	 * @param userId
	 *            用户标识
	 * @param initPwd
	 *            原始密码
	 * @param newPwd
	 *            新密码
	 * @param listener
	 * @param flag
	 */
	void changeUserPassword(Integer userId, String initPwd, String newPwd,
							OnResultListener listener, Boolean flag);

}
