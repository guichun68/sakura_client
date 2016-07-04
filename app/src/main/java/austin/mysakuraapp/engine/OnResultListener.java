package austin.mysakuraapp.engine;

/**
 * @Description 从服务器获取到数据后的回调接口
 * @author 
 */
public interface OnResultListener {

	/**
	 * 当获取到数据时回调
	 * 
	 * @param obj
	 * @param what
	 */
	void onGetData(Object obj, int what);

	/**
	 * 请求服务器失败时回调
	 */
	void onFailure(String msg);
}
