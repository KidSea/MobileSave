package com.example.mobilesavety.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 版本管理工具
 * 
 * @author yuxuehai
 * 
 */
public class PackageUtils {
	/**
	 * 编写获取版本号方法，返回版本号
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			// 获得包的管理者
			PackageManager mPackageManager = context.getPackageManager();
			// 返回包的基本信息
			// 第一个参数：包名
			// 第二个参数：标记,0代表获取到所有标记

			PackageInfo packageInfo = mPackageManager.getPackageInfo(
					context.getPackageName(), 0);
			//获取版本号
			String versionName = packageInfo.versionName;
			//返回版本号
			return versionName;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	/**
	 * 获取本地版本号
	 * @param context
	 * @return
	 */
	public static int getVersionCode (Context context){
		try {
			// 获得包的管理者
			PackageManager mPackageManager = context.getPackageManager();
			// 返回包的基本信息
			// 第一个参数：包名
			// 第二个参数：标记,0代表获取到所有标记

			PackageInfo packageInfo = mPackageManager.getPackageInfo(
					context.getPackageName(), 0);
			//获取版本号
			int versionName = packageInfo.versionCode;
			//返回版本号
			return versionName;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
}
