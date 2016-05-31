package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class setup4Activity extends BaseSetupActivity {
	
	private CheckBox cb_protect;
	
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		
		sp=getSharedPreferences("config",MODE_PRIVATE);

		cb_protect=(CheckBox) findViewById(R.id.cb_protect);
		
		boolean protecting=sp.getBoolean("protecting", false);	
		
		if(protecting){
			//�����Ѿ�����
			cb_protect.setText("�ֻ������Ѿ�����");
			cb_protect.setChecked(true);
			
		}else{
			//δ����
			cb_protect.setText("�ֻ�����δ����");
			cb_protect.setChecked(false);
			
		}
		
		
		cb_protect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					cb_protect.setText("�ֻ������Ѿ�����");
					
				}else{
					cb_protect.setText("�ֻ�����δ����");
					
				}
				
				//����ѡ���״̬
				Editor editor=sp.edit();
				editor.putBoolean("protecting", isChecked);
				
				editor.commit();
			}
		});
		
	}


	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		Editor editor=sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		Intent intent=new Intent(this,LostFindActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);		
	}


	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);		
	}
	
}
