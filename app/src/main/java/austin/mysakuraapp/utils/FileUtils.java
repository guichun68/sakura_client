package austin.mysakuraapp.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Created by austin on 2015/9/22.
 */
public class FileUtils {
	public static boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	public static String getExternalStoragePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/hifm/";
	}

	public static String getDownloadDir(Context context) {
		return getDir(context, "download");
	}

	public static String getCacheDir(Context context) {
		return getDir(context, "cache");
	}

	public static String getIconDir(Context context) {
		return getDir(context, "icon");
	}

	public static String getDir(Context context, String name) {
		String path;
		if (isSDCardAvailable()) {
			path = getExternalStoragePath();
		} else {
			path = getCachePath(context);
		}
		path = path + name + "/";
		if (createDirs(path)) {
			return path;
		} else {
			return null;
		}
	}
	
	
	/**得到缓存大小
	 * @param ctx
	 * @return
	 */
	public static String getCachSize(Context ctx){
		String path;
		String spaceAvail;
		if (isSDCardAvailable()) {
			path = getExternalStoragePath();
			spaceAvail = getSpaceAvail(ctx, path);
		} else {
			path = getCachePath(ctx);
			spaceAvail = getDataTotalSize(ctx);
		}
		return spaceAvail;
	}

	public static String getCachePath(Context context) {
		File f = context.getCacheDir();
		if (null == f) {
			return null;
		} else {
			return f.getAbsolutePath() + "/";
		}
	}
	
	/**获取手机内部空间大小
	 * @param context
	 * @return
	 */
	public static String getDataTotalSize(Context context){  
	    StatFs sf = new StatFs(context.getCacheDir().getAbsolutePath());  
	    long blockSize = sf.getBlockSize();  
	    long totalBlocks = sf.getBlockCount();  
	    return Formatter.formatFileSize(context, blockSize*totalBlocks);  
	}  

	public static boolean createDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}

	/**
	 * 得到指定存储位置容量
	 * 
	 * @param ctx
	 * @param path
	 * @return 容量大小，如 504M 、1.23G
	 */
	public static String getSpaceAvail(Context ctx, String path) {
		StatFs fs = new StatFs(path);
		// 得到区块大小
		long blockSize = fs.getBlockSize();
		// 得到区块数量
		long blocks = fs.getAvailableBlocks();

		return Formatter.formatFileSize(ctx, blockSize * blocks);

	}

	/**
	 * 得到指定存储位置容量,未经格式化的long值size
	 * 
	 * @param ctx
	 * @param path
	 * @return 容量大小，如 504M 、1.23G
	 */
	public static long getSpaceAvailNoFormated(Context ctx, String path) {
		StatFs fs = new StatFs(path);
		// 得到区块大小
		long blockSize = fs.getBlockSize();
		// 得到区块数量
		long blocks = fs.getAvailableBlocks();
		return blockSize * blocks;
	}


	/**
	 * 得到当前可用的空间大小（字节）
	 * 
	 * @param mContext
	 */
	public static long getCurrAvailStorage(Context mContext) {
		long spaceAvailNoFormated;
		if (FileUtils.isSDCardAvailable()) {
			String path2 = Environment.getExternalStorageDirectory().getAbsolutePath();
			spaceAvailNoFormated = FileUtils.getSpaceAvailNoFormated(mContext, path2);
		} else {
			spaceAvailNoFormated = FileUtils.getSpaceAvailNoFormated(mContext, Environment.getDataDirectory().getAbsolutePath());
		}
		return spaceAvailNoFormated;
	}
	
	/**得到本地所有音频文件大小
	 * @return
	 */
	public static long getAudioFileSize(Context ctx){
		String dir = getDir(ctx, "");
		String filePath = dir.substring(0, dir.length()-1);
		File file = new File(filePath);
		long size;
		try {
			 size = getFileSize(file);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return size;
	}
	
	// 递归方式取得文件夹大小
	public static long getFileSize(File f) throws Exception
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}


	

}