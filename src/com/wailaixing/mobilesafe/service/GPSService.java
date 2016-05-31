package com.wailaixing.mobilesafe.service;


import java.io.IOException;
import java.io.InputStream;

import android.app.LocalActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

public class GPSService extends Service {

	//位置服务
	private LocationManager lm;
	private MyLocationListener listener;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//得到位置服务
		lm=(LocationManager) getSystemService(LOCATION_SERVICE);
		//得到位置提供者
//		List<String> provider=lm.getAllProviders();
		
//		for(String s:provider){
//			System.out.println(s);
//		}
		
		listener=new MyLocationListener();
		//注册监听位置服务

		//为位置提供者设置条件
		Criteria criteria=new Criteria();
		//设置精确度
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//得到一个最好的位置提供者		
		String provider=lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(provider, 0, 0, listener);	
	
	}
	
	//取消监听
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//取消监听位置服务
		lm.removeUpdates(listener);
		listener=null;
	}
	

	class MyLocationListener implements LocationListener{

		//当位置改变
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			//得到经度
			String longiture="longitude:"+location.getLongitude()+"\n";
			//纬度
			String latitude="latitude:"+location.getLatitude()+"\n";
			
			//精确度
			String accuracy="accuracy:"+location.getAccuracy()+"\n";
			
			//火星坐标转化为标准坐标
			try {
				InputStream is=getAssets().open("axisoffset.dat");
				ModifyOffset offset=ModifyOffset.getInstance(is);
				PointDouble double1 =offset.s2c(new PointDouble(location.getLongitude(), location.getLatitude()));

				longiture="longiture: "+offset.X+"\n";
				latitude="latitude: "+offset.Y+"\n";
				
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//发送短信，给安全号码
			SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
			Editor editor=sp.edit();
			editor.putString("lastlocation", longiture+latitude+accuracy);
			editor.commit();
			
		}
		
		//位置提供者不可用，回调（gps\基站\ 网络）
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		//位置提供者可用，回调（gps\基站\ 网络）
		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		//状态改变，回调
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
		
	
	
	
}
