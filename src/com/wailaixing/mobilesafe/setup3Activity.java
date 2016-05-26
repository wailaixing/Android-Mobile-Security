package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class setup3Activity extends BaseSetupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}
	

	@Override
	public void showNext() {
		Intent intent = new Intent(this,setup4Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
		
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this,setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}
	
	//选择联系人的点击事件
	public void selectContact(View view){
		Intent intent=new Intent(this,SelectContactActivity.class);
		startActivityForResult(intent, 0);
		finish();
		
	}
	

}
