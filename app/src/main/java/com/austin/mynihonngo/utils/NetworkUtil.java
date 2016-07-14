package com.austin.mynihonngo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import com.austin.mynihonngo.comm.GlobalParams;

/**
 * 检查网络的工具类
 */
public class NetworkUtil {
	/**
	 * 检查网络
	 */
	public static boolean checkNetwork(Context context) {
		// 判断手机端利用的通信渠道

		// ①判断WIFI可以连接
		boolean isWIFI = isWIFICon(context);
		// ②判断MOBILE可以连接
		boolean isMOBILE = isMOBILECon(context);
		// 如果都无法使用——提示用户
		if (!isWIFI && !isMOBILE) {
			return false;
		}
		// 如果有可以利用的通信渠道，是不是MOBILE
		if (isMOBILE) {
			// 如果是，是否是wap方式
			// 读取APN配置信息，如果发现代理信息非空
			// readAPN(context);// 读取联系人信息
		}
		return true;
	}

	/**
	 * 读取APN配置信息
	 * 
	 * @param context
	 */
	private static void readAPN(Context context) {
		Uri PREFERRED_APN_URI = Uri
				.parse("content://telephony/carriers/preferapn");// 4.0模拟器屏蔽掉该权限

		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null,
				null);// 只有一条
		if (cursor != null && cursor.moveToFirst()) {
			GlobalParams.PROXY_IP = cursor.getString(cursor
					.getColumnIndex("proxy"));
			GlobalParams.PROXY_PORT = cursor.getInt(cursor
					.getColumnIndex("port"));
		}
	}

	/**
	 * 判断MOBILE可以连接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMOBILECon(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (networkInfo != null) {
			return networkInfo.isConnected();
		}

		return false;
	}

	/**
	 * 判断WIFI可以连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWIFICon(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
	
	
	/**
	 * 
	 * Role:Telecom service providers获取手机服务商信息 <BR>
	 * 
	 * 需要加入权限<uses-permission
	 * android:name="android.permission.READ_PHONE_STATE"/> <BR>
	 * Date:2012-3-12 <BR>
	 * 
	 * @author CODYY)allen
	 */
	public static String getProvidersName(Context context) {
		String ProvidersName = "nosim";
		try {
			// 返回唯一的用户ID;就是这张卡的编号神马的
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String IMSI = telephonyManager.getSubscriberId();
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002"))
				ProvidersName = "中国移动";
			else if (IMSI.startsWith("46001"))
				ProvidersName = "中国联通";
			else if (IMSI.startsWith("46003"))
				ProvidersName = "中国电信";
		} catch (Exception e) {
			e.printStackTrace();
			return ProvidersName;
		}
		return ProvidersName;
	}

	// 手机系统版本
	public static String getOsDisplay() {
		return Build.DISPLAY;
	}

	/**
	 * 获取手机号
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhone(Context context) {
		TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return phoneMgr.getLine1Number();
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getPhoneType() {
		return Build.MODEL;
	}

	/**
	 * 获取sdk版本
	 * 
	 * @return
	 */
	public static String getSDKVersion() {
		return Build.VERSION.SDK;
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 */
	public static String getVersion() {
		return Build.VERSION.RELEASE;
	}

	public static class TelephonyManagerInfo {
		/**
		 * 电话状态： 1.tm.CALL_STATE_IDLE=0 无活动
		 * 
		 * 2.tm.CALL_STATE_RINGING=1 响铃
		 * 
		 * 3.tm.CALL_STATE_OFFHOOK=2 摘机
		 */
		public static int CallState;
		/**
		 * 
		 * 电话方位：(需要权限：android.permission.ACCESS_COARSE_LOCATION)
		 */
		public static String CellLocation;

		/**
		 * 
		 * 唯一的设备ID：
		 * 
		 * GSM手机的 IMEI 和 CDMA手机的 MEID.
		 * 
		 * Return null if device ID is not available.
		 */
		public static String DeviceId;

		/**
		 * 
		 * 设备的软件版本号：
		 * 
		 * 例如：the IMEI/SV(software version) for GSM phones.
		 * 
		 * Return null if the software version is not available.
		 */
		public static String DeviceSoftwareVersion;

		/**
		 * 
		 * 手机号：
		 * 
		 * GSM手机的 MSISDN.
		 * 
		 * Return null if it is unavailable.
		 */
		public static String Line1Number;

		/**
		 * 
		 * 附近的电话的信息:
		 * 
		 * 类型：List<NeighboringCellInfo>
		 * 
		 * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
		 */
		public static List<NeighboringCellInfo> NeighboringCellInfo;// List<NeighboringCellInfo>

		/**
		 * 
		 * 获取ISO标准的国家码，即国际长途区号。
		 * 
		 * 注意：仅当用户已在网络注册后有效。
		 * 
		 * 在CDMA网络中结果也许不可靠。
		 */
		public static String NetworkCountryIso;

		/**
		 * 
		 * MCC+MNC(mobile country code + mobile network code)
		 * 
		 * 注意：仅当用户已在网络注册时有效。
		 * 
		 * 在CDMA网络中结果也许不可靠。
		 */
		public static String NetworkOperator;

		/**
		 * 
		 * 按照字母次序的current registered operator(当前已注册的用户)的名字
		 * 
		 * 注意：仅当用户已在网络注册时有效。
		 * 
		 * 在CDMA网络中结果也许不可靠。
		 */

		public static String NetworkOperatorName;// String

		/**
		 * 当前使用的网络类型：
		 * 
		 * 例如： NETWORK_TYPE_UNKNOWN 网络类型未知 0 NETWORK_TYPE_GPRS GPRS网络 1
		 * 
		 * NETWORK_TYPE_EDGE EDGE网络 2
		 * 
		 * NETWORK_TYPE_UMTS UMTS网络 3
		 * 
		 * NETWORK_TYPE_HSDPA HSDPA网络 8
		 * 
		 * NETWORK_TYPE_HSUPA HSUPA网络 9
		 * 
		 * NETWORK_TYPE_HSPA HSPA网络 10
		 * 
		 * NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4
		 * 
		 * NETWORK_TYPE_EVDO_0 EVDO网络, revision 0. 5
		 * 
		 * NETWORK_TYPE_EVDO_A EVDO网络, revision A. 6
		 * 
		 * NETWORK_TYPE_1xRTT 1xRTT网络 7
		 */
		public static int NetworkType;// int

		/**
		 * 
		 * 手机类型：
		 * 
		 * 例如： PHONE_TYPE_NONE 无信号
		 * 
		 * PHONE_TYPE_GSM GSM信号
		 * 
		 * PHONE_TYPE_CDMA CDMA信号
		 */

		public static int PhoneType;// int

		/**
		 * 
		 * Returns the ISO country code equivalent for the SIM provider's
		 * country code.
		 * 
		 * 获取ISO国家码，相当于提供SIM卡的国家码。
		 */
		public static String SimCountryIso;// String

		/**
		 * 
		 * Returns the MCC+MNC (mobile country code + mobile network code) of
		 * the provider of the SIM. 5 or 6 decimal digits.
		 * 
		 * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
		 * 
		 * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
		 */
		public static String SimOperator;// String

		/**
		 * 
		 * 服务商名称：
		 * 
		 * 例如：中国移动、联通
		 * 
		 * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
		 */
		public static String SimOperatorName;// String

		/**
		 * 
		 * SIM卡的序列号：
		 * 
		 * 需要权限：READ_PHONE_STATE
		 */
		public static String SimSerialNumber;// String

		/**
		 * 
		 * SIM的状态信息：
		 * 
		 * SIM_STATE_UNKNOWN 未知状态 0
		 * 
		 * SIM_STATE_ABSENT 没插卡 1
		 * 
		 * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2
		 * 
		 * SIM_STATE_PUK_REQUIRED 锁定状态，需要用户的PUK码解锁 3
		 * 
		 * SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
		 * 
		 * SIM_STATE_READY 就绪状态 5
		 */
		public static int SimState;// int

		/**
		 * 
		 * 唯一的用户ID：
		 * 
		 * 例如：IMSI(国际移动用户识别码) for a GSM phone.
		 * 
		 * 需要权限：READ_PHONE_STATE
		 */
		public static String SubscriberId;// String

		/**
		 * 
		 * 取得和语音邮件相关的标签，即为识别符
		 * 
		 * 需要权限：READ_PHONE_STATE
		 */
		public static String VoiceMailAlphaTag;// String

		/**
		 * 
		 * 获取语音邮件号码：
		 * 
		 * 需要权限：READ_PHONE_STATE
		 */
		public static String VoiceMailNumber;// String

		/**
		 * 
		 * ICC卡是否存在
		 */
		public static boolean hasIccCard;// boolean

		/**
		 * 
		 * 是否漫游:
		 * 
		 * (在GSM用途下)
		 */
		public static boolean isNetworkRoaming;
	}

	/**
	 * 获取手机唯一ID
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneUniqueId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获取手机信息实体
	 * 
	 * @param context
	 * @return
	 */
	public static TelephonyManagerInfo getTelephonyInfo(Context context) {
		TelephonyManagerInfo info = new TelephonyManagerInfo();
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		info.CallState = tm.getCallState();
		info.CellLocation = tm.getCellLocation() != null ? tm.getCellLocation().toString() : "";
		info.DeviceId = tm.getDeviceId();
		info.DeviceSoftwareVersion = tm.getDeviceSoftwareVersion();
		info.hasIccCard = tm.hasIccCard();
		info.isNetworkRoaming = tm.isNetworkRoaming();
		info.Line1Number = tm.getLine1Number();
		info.NeighboringCellInfo = tm.getNeighboringCellInfo();
		info.NetworkCountryIso = tm.getNetworkCountryIso();
		info.NetworkOperator = tm.getNetworkOperator();
		info.NetworkOperatorName = tm.getNetworkOperatorName();
		info.NetworkType = tm.getNetworkType();
		info.PhoneType = tm.getPhoneType();
		info.SimCountryIso = tm.getSimCountryIso();
		info.SimOperator = tm.getSimOperator();
		info.SimOperatorName = tm.getSimOperatorName();
		info.SimSerialNumber = tm.getSimSerialNumber();
		info.SimState = tm.getSimState();
		info.SubscriberId = tm.getSubscriberId();
		info.VoiceMailAlphaTag = tm.getVoiceMailAlphaTag();
		info.VoiceMailNumber = tm.getVoiceMailNumber();
		return info;
	}

	/** 返回当前网速 */
	public static long getNetworkSpeed() {
		// TODO Auto-generated method stub
		ProcessBuilder cmd;
		long readBytes = 0;
		BufferedReader rd = null;
		try {
			String[] args = { "/system/bin/cat", "/proc/net/dev" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			int linecount = 0;
			while ((line = rd.readLine()) != null) {
				linecount++;
				if (line.contains("wlan0") || line.contains("eth0")) {
					// L.E("获取流量整条字符串",line);
					String[] delim = line.split(":");
					if (delim.length >= 2) {
						String[] numbers = delim[1].trim().split(" ");// 提取数据
						readBytes = Long.parseLong(numbers[0].trim());//
						break;
					}
				}
			}
			rd.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return readBytes;
	}



	/**
	 * 检查手机网络(非wifi)是否有用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
				return false;
			} else {
				if (info.isAvailable()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否为wifi的连接ip
	 * 
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		int ipAddress = getWifiIpInfo(context);
		if (ipAddress > 0)
			return true;
		else
			return false;
	}

	private static int getWifiIpInfo(Context context) {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		// return String.valueOf(wifiInfo.getIpAddress());
		int ipAddress = wifiInfo.getIpAddress();
		return ipAddress;
	}

	/**
	 * 获取wifi ip
	 * 
	 * @return
	 */
	public static String getWifiAddress(Context context) {
		int ipAddress = getWifiIpInfo(context);
		return intToIp(ipAddress);
	}

	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}

	/**
	 * 获取手机mac地址<br/>
	 * 错误返回12个0
	 */
	public static String getMacAddress(Context context) {
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	/**
	 * 获取手机ip(此方法在android中使用获取3G网络ip地址)
	 * 
	 * @return
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public static String getLocalIpAddress() throws SocketException {
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress()) {
					return inetAddress.getHostAddress().toString();
				}
			}
		}
		return null;
	}

	/**
	 * 获取本机ip(此方法仅在java程序中)
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostAddress() throws UnknownHostException {
		InetAddress address = null;
		address = InetAddress.getLocalHost();
		return address.getHostAddress();
	}

	public static String GetNetworkType(Context ctx)
	{
	    String strNetworkType = "";
	    
	    NetworkInfo networkInfo = ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected())
	    {
	        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
	        {
	            strNetworkType = "WIFI";
	        }
	        else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
	        {
	            String _strSubTypeName = networkInfo.getSubtypeName();
	            
//	            Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);
	            
	            // TD-SCDMA   networkType is 17
	            int networkType = networkInfo.getSubtype();
	            switch (networkType) {
	                case TelephonyManager.NETWORK_TYPE_GPRS:
	                case TelephonyManager.NETWORK_TYPE_EDGE:
	                case TelephonyManager.NETWORK_TYPE_CDMA:
	                case TelephonyManager.NETWORK_TYPE_1xRTT:
	                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
	                    strNetworkType = "2G";
	                    break;
	                case TelephonyManager.NETWORK_TYPE_UMTS:
	                case TelephonyManager.NETWORK_TYPE_EVDO_0:
	                case TelephonyManager.NETWORK_TYPE_EVDO_A:
	                case TelephonyManager.NETWORK_TYPE_HSDPA:
	                case TelephonyManager.NETWORK_TYPE_HSUPA:
	                case TelephonyManager.NETWORK_TYPE_HSPA:
	                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
	                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
	                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
	                    strNetworkType = "3G";
	                    break;
	                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
	                    strNetworkType = "4G";
	                    break;
	                default:
	                    // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
	                    if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) 
	                    {
	                        strNetworkType = "3G";
	                    }
	                    else
	                    {
	                        strNetworkType = _strSubTypeName;
	                    }
	                    
	                    break;
	             }
	             
//	            Log.e("cocos2d-x", "Network getSubtype : " + Integer.valueOf(networkType).toString());
	        }
	    }
	    
//	    Log.e("cocos2d-x", "Network Type : " + strNetworkType);
	    
	    return strNetworkType;
	}
	
}
