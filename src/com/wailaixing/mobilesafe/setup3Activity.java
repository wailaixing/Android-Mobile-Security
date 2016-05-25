package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class setup3Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		
	}
	
	public void next(View view){
		Intent intent=new Intent(this,setup4Activity.class);
		startActivity(intent);
		finish();
	}
	
	public void pre(View view){
		Intent intent=new Intent(this,setup2Activity.class);
		startActivity(intent);
		finish();
	}
	
}
