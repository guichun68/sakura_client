package austin.mysakuraapp.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpUtil {

	public static boolean isHttps = true;

	private final static String TAG = "HttpUtil";

	private InputStream stream;
	private char[] password;

	public HttpUtil(InputStream stream, char[] password) {
		this.stream = stream;
		this.password = password;
	}

	public class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance(TLS);

		public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new EasyX509TrustManager(truststore);

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
				UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	public class HttpsClient extends DefaultHttpClient {
		@Override
		protected ClientConnectionManager createClientConnectionManager() {
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

			// 用我们自己定义的 SSLSocketFactory 在 ConnectionManager 中注册一个 443 端口
			registry.register(new Scheme("https", newSslSocketFactory(), 443));
			return new SingleClientConnManager(getParams(), registry);
		}

		private SSLSocketFactory newSslSocketFactory() {
			try {
				// Get an instance of the Bouncy Castle KeyStore format
				KeyStore trusted = KeyStore.getInstance("BKS");

				// 用 keystore 的密码跟证书初始化 trusted
				trusted.load(stream, password);

				SSLSocketFactory sf = new SSLSocketFactory(trusted);
				sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER); // 这个参数可以根据需要调整,
				return sf;
			} catch (Exception e) {
				Log.i(TAG, e.getMessage());
				return null;
			}

		}
	}

	private HttpClient getNewHttpClient(InputStream stream, char[] password) {
		try {
			KeyStore trustStore = KeyStore.getInstance("PKCS12");
			trustStore.load(stream, password);

			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);

			if (stream == null || password == null) {
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			} else {
				sf.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			}

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 8443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			DefaultHttpClient defaultHttpClient = new DefaultHttpClient(ccm, params);
			return defaultHttpClient;
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
			return new DefaultHttpClient();
		}
	}

	public String getRequest(String urlString, Map<String, String> params) {

		try {
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(urlString);

			if (null != params) {

				urlBuilder.append("?");

				Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

				while (iterator.hasNext()) {
					Entry<String, String> param = iterator.next();
					urlBuilder.append(URLEncoder.encode(param.getKey(), "UTF-8")).append('=')
							.append(URLEncoder.encode(param.getValue(), "UTF-8"));
					if (iterator.hasNext()) {
						urlBuilder.append('&');
					}
				}
			}
			// 创建HttpClient对象
			HttpClient client = getNewHttpClient(null, null);
			// 发送get请求创建HttpGet对象
			HttpGet getMethod = new HttpGet(urlBuilder.toString());
			HttpResponse response = client.execute(getMethod);
			// 获取状态码
			int res = response.getStatusLine().getStatusCode();
			if (res == 200) {

				StringBuilder builder = new StringBuilder();
				// 获取响应内容
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				for (String s = reader.readLine(); s != null; s = reader.readLine()) {
					builder.append(s);
				}
				return builder.toString();
			}
		} catch (Exception e) {

		}

		return null;
	}

	public String postRequest(String urlString, List<BasicNameValuePair> params) {

		// 1. 创建HttpClient对象
		HttpClient client = null;
		try {

			if (stream != null && password != null) {
				isHttps = true;
				client = new CustomerHttpClient().getHttpClient();
			} else {
				isHttps = false;
				client = getNewHttpClient(stream, password);
			}

			// 2. 发get请求创建HttpGet对象
			HttpPost postMethod = new HttpPost(urlString);
			postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = client.execute(postMethod);
			int statueCode = response.getStatusLine().getStatusCode();
			Log.i(TAG, "statueCode" + statueCode);
			if (statueCode == 200) {
				String content = null;
				if (stream != null && password != null) {
					content = EntityUtils.toString(response.getEntity());
					String json = JSON.parseObject(content, String.class);
//					Log.i(TAG, ">>>>>content " + json);

				} else {
					content = EntityUtils.toString(response.getEntity());
//					Log.i(TAG, ">>>>>content " + content);

				}
				return content;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());
			return null;

		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}

		return null;
	}

	public class EasyX509TrustManager implements X509TrustManager {
		private X509TrustManager standardTrustManager = null;

		/**
		 * Constructor for EasyX509TrustManager.
		 */
		public EasyX509TrustManager(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
			super();
			TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			factory.init(keystore);
			TrustManager[] trustmanagers = factory.getTrustManagers();
			if (trustmanagers.length == 0) {
				throw new NoSuchAlgorithmException("no trust manager found");
			}
			this.standardTrustManager = (X509TrustManager) trustmanagers[0];
		}

		/**
		 * @see 
		 *      X509TrustManager#checkClientTrusted(X509Certificate
		 *      [], String authType)
		 */
		public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
			standardTrustManager.checkClientTrusted(certificates, authType);
		}

		/**
		 * @see
		 *      X509TrustManager#checkServerTrusted(X509Certificate
		 *      [], String authType)
		 */
		public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
			// if ((certificates != null)) {
			// Log.d(TAG, "Server certificate chain:");
			// for (int i = 0; i < certificates.length; i++) {
			// Log.d(TAG, "X509Certificate[" + i + "]=" + certificates[i]);
			// }
			// }
			if ((certificates != null) && (certificates.length == 1)) {
				certificates[0].checkValidity();
			} else {
				standardTrustManager.checkServerTrusted(certificates, authType);
			}
		}

		/**
		 * @see X509TrustManager#getAcceptedIssuers()
		 */
		public X509Certificate[] getAcceptedIssuers() {
			return this.standardTrustManager.getAcceptedIssuers();
		}
	}

	public class CustomerHttpClient {
		private static final String CHARSET = HTTP.UTF_8;
		private HttpClient customerHttpClient;

		private CustomerHttpClient() {
		}

		public synchronized HttpClient getHttpClient() {
			if (null == customerHttpClient) {
				HttpParams params = new BasicHttpParams();
				// 设置一些基本参数
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(params, CHARSET);
				HttpProtocolParams.setUseExpectContinue(params, true);
				HttpProtocolParams.setUserAgent(params,
						"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
								+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
				// 超时设置
				/* 从连接池中取连接的超时时间 */
				ConnManagerParams.setTimeout(params, 10000);
				/* 连接超时 */
				HttpConnectionParams.setConnectionTimeout(params, 30000);
				HttpConnectionParams.setTcpNoDelay(params, true);
				/* 请求超时 */
				HttpConnectionParams.setSoTimeout(params, 30000);

				KeyStore trustStore;
				SSLSocketFactory sf = null;
				try {
					trustStore = KeyStore.getInstance("PKCS12");
					trustStore.load(stream, password);

					sf = new SSLSocketFactoryEx(trustStore);

					if (stream == null || password == null) {
						sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					} else {
						sf.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
					}
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					SchemeRegistry schReg = new SchemeRegistry();
					schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
					schReg.register(new Scheme("https", sf, 8443));

					// 使用线程安全的连接管理来创建HttpClient
					ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
					customerHttpClient = new DefaultHttpClient(conMgr, params);
				} catch (Exception e) {
					Log.i(TAG, e.getMessage());
				}
			}
			return customerHttpClient;
		}
	}
}
