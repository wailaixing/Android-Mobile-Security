package com.wailaixing.mobilesafe.engine;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.wailaixing.mobilesafe.domain.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

//ҵ�񷽷����ṩ�ֻ����氲װ������Ӧ����Ϣ
public class AppInfoProvider {
	
	//��ȡ��װ��Ӧ����Ϣ,��õ�PackaManage ����ĵ�������
	public static  List<AppInfo> getAppInfos(Context context){
		PackageManager pm=context.getPackageManager();
		//�õ������ڸ��豸�ϰ�װ��Ӧ�õ���Ϣ,flags���ӵĿ�ѡ���
		List<PackageInfo> packInfos=pm.getInstalledPackages(0);
		List<AppInfo> appInfos=new ArrayList<AppInfo>();
		
		for(PackageInfo packInfo :packInfos){
			//packInfo�൱��һ��Ӧ�ó���apk�����嵥�ļ�
			String packname=packInfo.packageName;
			Drawable icon=packInfo.applicationInfo.loadIcon(pm);
			String name=packInfo.applicationInfo.loadLabel(pm).toString();
			
			//Ӧ�ó�����Ϣ���
			int flags=packInfo.applicationInfo.flags;
			//ApplicationInfo
			
			
			AppInfo appInfo=new AppInfo();
			appInfo.setIcon(icon);
			appInfo.setPackname(packname);
			appInfo.setName(name);
			
			appInfos.add(appInfo);
			
		}
		return appInfos;
	}
	
	
}
