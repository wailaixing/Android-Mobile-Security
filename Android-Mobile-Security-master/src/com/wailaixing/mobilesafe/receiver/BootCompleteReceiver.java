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

	//��ȡSIM������Ϣ
	private TelephonyManager tm;
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//��ȡ֮ǰ��SIM����Ϣ�� Context.MODE_PRIVATE !!!!
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		Boolean protecting=sp.getBoolean("protecting", false);
		
		//������������ִ��
		if(protecting){
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			//��ȡ֮ǰ�����SIM����Ϣ
			String saveSim=sp.getString("sim", "");
			
			//��ȡ��ǰ��SIM����Ϣ
			String realsim=tm.getSimSerialNumber();		
			
			//���бȽ�
			if(saveSim.equals(realsim)){
				//SIM��δ���
			}else{
				//SIM�����
				//�������Ÿ���ȫ����
				Toast.makeText(context, "SIM�����", 0).show();
				System.out.println("SIM�����");
				
				SmsManager.getDefault().sendTextMessage(sp.getString("safenumber", ""),null,"SIM�����",null,null);
			
			}			
			
		}
	}
}
