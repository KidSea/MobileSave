package com.example.mobilesavety.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * �汾������
 * 
 * @author yuxuehai
 * 
 */
public class PackageUtils {
	/**
	 * ��д��ȡ�汾�ŷ��������ذ汾��
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			// ��ð��Ĺ�����
			PackageManager mPackageManager = context.getPackageManager();
			// ���ذ��Ļ�����Ϣ
			// ��һ������������
			// �ڶ������������,0�����ȡ�����б��

			PackageInfo packageInfo = mPackageManager.getPackageInfo(
					context.getPackageName(), 0);
			//��ȡ�汾��
			String versionName = packageInfo.versionName;
			//���ذ汾��
			return versionName;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	/**
	 * ��ȡ���ذ汾��
	 * @param context
	 * @return
	 */
	public static int getVersionCode (Context context){
		try {
			// ��ð��Ĺ�����
			PackageManager mPackageManager = context.getPackageManager();
			// ���ذ��Ļ�����Ϣ
			// ��һ������������
			// �ڶ������������,0�����ȡ�����б��

			PackageInfo packageInfo = mPackageManager.getPackageInfo(
					context.getPackageName(), 0);
			//��ȡ�汾��
			int versionName = packageInfo.versionCode;
			//���ذ汾��
			return versionName;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
}
