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
	
	//����¼��������������ز�ѯ
	public void numberQuery(View view){
		Intent intentv=new Intent(this,NumberAddressQueryActivity.class);
		startActivity(intentv);
		
		
	}
	
	
}
