package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AtoolsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	
	}
	
	//点击事件，进入号码归属地查询
	public void numberQuery(View view){
		Intent intentv=new Intent(this,NumberAddressQueryActivity.class);
		startActivity(intentv);
		
		
	}
	
	
}
