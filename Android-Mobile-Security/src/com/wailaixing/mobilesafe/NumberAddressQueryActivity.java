package com.wailaixing.mobilesafe;

import com.wailaixing.mobilesafe.db.dao.NumberAddressQueryUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberAddressQueryActivity extends Activity{
	
	private static final String TAG = "NumberAddressQueryActivity";
	private EditText ed_phone;
	private TextView result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);
		ed_phone=(EditText)findViewById(R.id.ed_phone);
		result=(TextView)findViewById(R.id.result);
	}
	
	
	//查询号码归属地
	public void numberAddressQuery(View view){
		String phone=ed_phone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			Toast.makeText(this, "号码为空", 0).show();
			return ;
		}else{
			//查询数据库
			//
			
			String address=NumberAddressQueryUtils.queryNumber(phone);
			
			result.setText(address);
			
			Log.i(TAG, "查询数据库");
			
		}
		
	}
	
	
}
