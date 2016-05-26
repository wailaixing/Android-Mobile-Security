package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;


public class setup1Activity extends BaseSetupActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);

	}
	


	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, setup2Activity.class);
		startActivity(intent);
		finish();
		// 要求在startActivity() 和finish()后面
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);		
	}

	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		
	}
	
}
