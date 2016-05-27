package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class setup3Activity extends BaseSetupActivity {
	
	
	private EditText et_setup3_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		et_setup3_phone=(EditText)findViewById(R.id.et_setup3_phone);
		et_setup3_phone.setText(sp.getString("safenumber", ""));
		
	}
	

	@Override
	public void showNext() {
		String phone = et_setup3_phone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			Toast.makeText(this, "��ȫ���뻹û������", 0).show();
			return;
		}
		
		//Ӧ�ñ���һЩ ��ȫ����
		Editor editor = sp.edit();
		editor.putString("safenumber", phone);
		editor.commit();
		
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
	
	//ѡ����ϵ�˵ĵ���¼�
	public void selectContact(View view){
		Intent intent=new Intent(this,SelectContactActivity.class);
		startActivityForResult(intent, 0);

		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data==null){
			return;
		}
		String phone=data.getStringExtra("phone").replace("-", "");
		
		et_setup3_phone.setText(phone);
	}
	
	

}
