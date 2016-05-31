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

	//λ�÷���
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
		//�õ�λ�÷���
		lm=(LocationManager) getSystemService(LOCATION_SERVICE);
		//�õ�λ���ṩ��
//		List<String> provider=lm.getAllProviders();
		
//		for(String s:provider){
//			System.out.println(s);
//		}
		
		listener=new MyLocationListener();
		//ע�����λ�÷���

		//Ϊλ���ṩ����������
		Criteria criteria=new Criteria();
		//���þ�ȷ��
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//�õ�һ����õ�λ���ṩ��		
		String provider=lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(provider, 0, 0, listener);	
	
	}
	
	//ȡ������
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//ȡ������λ�÷���
		lm.removeUpdates(listener);
		listener=null;
	}
	

	class MyLocationListener implements LocationListener{

		//��λ�øı�
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			//�õ�����
			String longiture="longitude:"+location.getLongitude()+"\n";
			//γ��
			String latitude="latitude:"+location.getLatitude()+"\n";
			
			//��ȷ��
			String accuracy="accuracy:"+location.getAccuracy()+"\n";
			
			//��������ת��Ϊ��׼����
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
			
			//���Ͷ��ţ�����ȫ����
			SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
			Editor editor=sp.edit();
			editor.putString("lastlocation", longiture+latitude+accuracy);
			editor.commit();
			
		}
		
		//λ���ṩ�߲����ã��ص���gps\��վ\ ���磩
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		//λ���ṩ�߿��ã��ص���gps\��վ\ ���磩
		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		//״̬�ı䣬�ص�
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
		
	
	
	
}
