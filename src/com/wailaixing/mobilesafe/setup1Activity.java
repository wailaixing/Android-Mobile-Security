package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.View;

public class setup1Activity extends Activity {
	//����һ������ʶ����
	private GestureDetector detector;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		//ʵ��������ʶ����
	}
	
	//�����һ��
	public void next(View view){
		Intent intent=new Intent(this,setup2Activity.class);
		startActivity(intent);
		finish();
		//Ҫ����startActivity() ��finish()����
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	
}
