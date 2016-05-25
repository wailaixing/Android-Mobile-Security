package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.View;

public class setup1Activity extends Activity {
	//定义一个手势识别器
	private GestureDetector detector;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		//实例化手势识别器
	}
	
	//点击下一步
	public void next(View view){
		Intent intent=new Intent(this,setup2Activity.class);
		startActivity(intent);
		finish();
		//要求在startActivity() 和finish()后面
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	
}
