package com.wailaixing.mobilesafe;

import com.wailaixing.mobilesafe.ui.SettingItemView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	
	private SharedPreferences sp;
	private SettingItemView siv_update;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		siv_update=(SettingItemView)findViewById(R.id.siv_update);
		
		boolean update=sp.getBoolean("update", false);
		if(update){
			//�Զ���������
			siv_update.setChecked(true);			
		}else{
			//�Զ������Ѿ��ر�
			siv_update.setChecked(false);			
		}
		
		siv_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor=sp.edit();
				// TODO Auto-generated method stub
				//�ж��Ƿ�ѡ��
				if(siv_update.isCheck()){
					//�Ѿ����Զ�����
					siv_update.setChecked(false);
					siv_update.setDesc("�Զ������Ѿ��ر�");
					editor.putBoolean("update", false);
				}else{
					//û�д��Զ�����
					siv_update.setChecked(true);
					siv_update.setDesc("�Զ������Ѿ�����");
					editor.putBoolean("update", true);
				}
				editor.commit();
			}
		});
	}
}
