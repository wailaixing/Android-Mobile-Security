package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class setup4Activity extends Activity {
	
	
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		sp=getSharedPreferences("config",MODE_PRIVATE);
		
	}
	
	
	public void next(View view){
		Editor editor=sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		Intent intent=new Intent(this,LostFindActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
	}
	
	
	
	public void pre(View view){
		Intent intent=new Intent(this,setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	
}
