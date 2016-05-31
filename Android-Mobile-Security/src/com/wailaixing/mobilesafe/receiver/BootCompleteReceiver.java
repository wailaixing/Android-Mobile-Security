package com.wailaixing.mobilesafe.receiver;

import java.net.ContentHandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {

	//读取SIM卡的信息
	private TelephonyManager tm;
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//读取之前的SIM卡信息， Context.MODE_PRIVATE !!!!
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		Boolean protecting=sp.getBoolean("protecting", false);
		
		//开启防盗，就执行
		if(protecting){
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			//读取之前保存的SIM卡信息
			String saveSim=sp.getString("sim", "");
			
			//读取当前的SIM卡信息
			String realsim=tm.getSimSerialNumber();		
			
			//进行比较
			if(saveSim.equals(realsim)){
				//SIM卡未变更
			}else{
				//SIM卡变更
				//发个短信给安全号码
				Toast.makeText(context, "SIM卡变更", 0).show();
				System.out.println("SIM卡变更");
				
				SmsManager.getDefault().sendTextMessage(sp.getString("safenumber", ""),null,"SIM卡变更",null,null);
			
			}			
			
		}
	}
}
