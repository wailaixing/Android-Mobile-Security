package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class setup1Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		
	}
	
	//�����һ��
	public void next(View view){
		Intent intent=new Intent(this,setup2Activity.class);
		startActivity(intent);
		finish();
		
	}
	
}
