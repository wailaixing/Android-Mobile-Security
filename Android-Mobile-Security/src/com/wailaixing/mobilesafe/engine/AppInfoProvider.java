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

//业务方法，提供手机里面安装的所有应用信息
public class AppInfoProvider {
	
	//获取安装的应用信息,想得到PackaManage 必须的到上下文
	public static  List<AppInfo> getAppInfos(Context context){
		PackageManager pm=context.getPackageManager();
		//得到所有在该设备上安装的应用的信息,flags附加的可选标记
		List<PackageInfo> packInfos=pm.getInstalledPackages(0);
		List<AppInfo> appInfos=new ArrayList<AppInfo>();
		
		for(PackageInfo packInfo :packInfos){
			//packInfo相当于一个应用程序apk包的清单文件
			String packname=packInfo.packageName;
			Drawable icon=packInfo.applicationInfo.loadIcon(pm);
			String name=packInfo.applicationInfo.loadLabel(pm).toString();
			
			//应用程序信息标记
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
