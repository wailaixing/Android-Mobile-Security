package com.wailaixing.mobilesafe.receiver;

import java.io.FileFilter;
import java.io.ObjectStreamConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.MediaStore.Audio.Media;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import com.wailaixing.mobilesafe.R;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	private SharedPreferences sp;
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		
		//���ն���
		Object[] objects=(Object[]) intent.getExtras().get("pdus");
		

		for(Object b:objects){
			//�����ĳ������
			SmsMessage sms=SmsMessage.createFromPdu((byte[]) b);
			
			//������
			String sender=sms.getOriginatingAddress();
			//����
			String body=sms.getMessageBody();
			
			String safesender=sp.getString("safenumber", "");
			
			if(sender.contains(safesender)){
				if("#*location*#".equals(body)){
					//�õ�GPSλ��
					Log.i(TAG, "GPS");
					//��ֹ�㲥
					abortBroadcast();
					
				}else if("#*alarm*".equals(body)){
					//���ž���
					Log.i(TAG, "alarm");
					MediaPlayer player=MediaPlayer.create(context, R.raw.mo);
					player.start();
					player.setLooping(true);
					player.setVolume(1.0f, 1.0f);
					abortBroadcast();
					
				}else if("#*wipedata*#".equals(body)){
					//��������
					Log.i(TAG, "wipedata");
					abortBroadcast();

				}else if("#*lockscreen*#".equals(body)){
					//����
					Log.i(TAG, "lockscreen");
					abortBroadcast();

				}	
				
			}
			
		}
		
		
	}

}
