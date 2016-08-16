package com.example.mobilesavety.engine;

import java.util.ArrayList;
import java.util.List;

import com.example.mobilesavety.domain.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;



/**
 * Ӧ����Ϣ�ṩ��
 * @author yuxuehai
 *
 */
public class AppInfoProvider {
	/**
	 * ���ص�ǰ�ֻ����е�Ӧ�õ������Ϣ(����,����,ͼ��,(�ֻ��ڴ�,sd��),(ϵͳ,�û�));
	 * @param ctx	��ȡ�������ߵ������Ļ���
	 * @return	�����ֻ���װӦ�������Ϣ�ļ���
	 */
	public static List<AppInfo> getAppInfoList(Context ctx){
		//1,���Ĺ����߶���
		PackageManager pm = ctx.getPackageManager();
		//2,��ȡ��װ���ֻ���Ӧ�������Ϣ�ļ���
		List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
		List<AppInfo> appInfoList = new ArrayList<AppInfo>();
		//3,ѭ������Ӧ����Ϣ�ļ���
		for (PackageInfo packageInfo : packageInfoList) {
			AppInfo appInfo = new AppInfo();
			//4,��ȡӦ�õİ���
			appInfo.packageName = packageInfo.packageName;
			//5,Ӧ������
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			//applicationInfo.uid��ȡÿһ��Ӧ�õ�Ψһ�Ա�ʾ
			appInfo.name = applicationInfo.loadLabel(pm).toString()+applicationInfo.uid;
			//6,��ȡͼ��
			appInfo.icon = applicationInfo.loadIcon(pm);
			//7,�ж��Ƿ�ΪϵͳӦ��(ÿһ���ֻ��ϵ�Ӧ�ö�Ӧ��flag����һ��)
			if((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM){
				//ϵͳӦ��
				appInfo.isSystem = true;
			}else{
				//��ϵͳӦ��
				appInfo.isSystem = false;
			}
			//8,�Ƿ�Ϊsd���а�װӦ��
			if((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)==ApplicationInfo.FLAG_EXTERNAL_STORAGE){
				//ϵͳӦ��
				appInfo.isSdCard = true;
			}else{
				//��ϵͳӦ��
				appInfo.isSdCard = false;
			}
			appInfoList.add(appInfo);
		}
		return appInfoList;
	}
}
