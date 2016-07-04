package austin.mysakuraapp.engine;

import com.alibaba.fastjson.JSONObject;

import austin.mysakuraapp.engine.nohttp.HttpListener;

/**APP相关接口，如检查更新等。
 * @author Austin
 *
 */
public interface IAppEngine {
//	Request<String> request = NoHttp.createStringRequest(url, requestMethod);
	/**检查更新
	 * @param requset
	 * @param listener
	 * @param flag
	 */
	void checkUpdate(int versionCode, HttpListener<JSONObject> callback, Boolean flag);
	
}
