package com.wailaixing.mobilesafe.receiver;

import java.io.FileFilter;
import java.io.ObjectStreamConstants;

import android.R.bool;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.MediaStore.Audio.Media;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.wailaixing.mobilesafe.R;
import com.wailaixing.mobilesafe.service.GPSService;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	private SharedPreferences sp;


	//�豸���Թ���Ա(����)
	//private DevicePolicyManager dpm;	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// д���ն��ŵĴ���
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		//dpm=(DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);		
		
		for(Object b:objs){
			//�����ĳһ������
			SmsMessage sms =SmsMessage.createFromPdu((byte[]) b);
			//������
			String sender = sms.getOriginatingAddress();

			String safenumber = sp.getString("safenumber", "");

			

			Log.i(TAG, "====sender=="+sender);
			String body = sms.getMessageBody();
			
//			Toast.makeText(context, sender, 0).show();
//			Toast.makeText(context, safenumber, 0).show();
//			Toast.makeText(context, body, 0).show();
			
			safenumber.replaceAll(" ", "");
			if(sender.contains(safenumber)){
				
				if("#*location*#".equals(body)){
					//�õ��ֻ���GPS
					Log.i(TAG, "�õ��ֻ���GPS");
					//��������
					Intent i = new Intent(context,GPSService.class);
					context.startService(i);
					SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
					String lastlocation = sp.getString("lastlocation", null);
					if(TextUtils.isEmpty(lastlocation)){
						//λ��û�еõ�
						SmsManager.getDefault().sendTextMessage(sender, null, "geting loaction.....", null, null);
					}else{ 
						SmsManager.getDefault().sendTextMessage(sender, null, lastlocation, null, null);
					}
					
					
					//������㲥��ֹ��
					abortBroadcast();
				}else if("#*alarm*#".equals(body)){
					//���ű���Ӱ��
					Log.i(TAG, "���ű���Ӱ��");
					
					//Toast.makeText(context, "ִ��alarm", 0).show();
					MediaPlayer player = MediaPlayer.create(context, R.raw.mo);
					player.setLooping(false);//
					player.setVolume(1.0f, 1.0f);
					player.start();
					
					abortBroadcast();
				}
				else if("#*wipedata*#".equals(body)){
					//Զ���������
					Log.i(TAG, "Զ���������");
					abortBroadcast();
				}
				else if("#*lockscreen*#".equals(body)){
					//Զ������
					Log.i(TAG, "Զ������");
					
					
					
					abortBroadcast();
				}
			}
		}
	}
}
