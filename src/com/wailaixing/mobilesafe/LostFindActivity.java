package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends Activity {

	private SharedPreferences sp;
	private TextView tv_safe_number;
	private ImageView iv_protecting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		//tv_safe_number=(TextView)findViewById(R.id.tv_safe_number);
		
		//�ж�һ�£��Ƿ����������򵼣����û������������ת��������ҳ��ȥ���ã���������ŵ�ǰ��ҳ��
		boolean configed = sp.getBoolean("configed", false);
		
		if(configed){
			//������������򵼣���ͣ�����ֻ�����ҳ��
			setContentView(R.layout.activity_lost_find);		
			
			//�õ���ȫ����Ŀؼ�
			tv_safe_number=(TextView)findViewById(R.id.tv_safe_number);
			iv_protecting=(ImageView)findViewById(R.id.iv_protecting);
			
			
			//�õ���ȫ����,����
			String safenumber=sp.getString("safenumber", "");
			tv_safe_number.setText(safenumber);
			
			//���÷���״̬
			Boolean protecting=sp.getBoolean("protecting", false);
			if(protecting){
				//��������
				iv_protecting.setImageResource(R.drawable.lock);
				
			}else{
				//δ��������
				iv_protecting.setImageResource(R.drawable.unlock);
				
			}
			
			
		}else{
			//û����������,����������
			Intent intent=new Intent(this,setup1Activity.class);
			startActivity(intent);
			//�رյ�ǰҳ��
			finish();
		}
		
	}
	//���½����ֻ�����ҳ��
	public void reEnterSetup(View View){
		Intent intent=new Intent(this,setup1Activity.class);
		startActivity(intent);
		finish();
	
	}
	
	
}
