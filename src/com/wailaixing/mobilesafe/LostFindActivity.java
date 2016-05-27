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
		
		//判断一下，是否做过设置向导，如果没有做过，就跳转到设置向导页面去设置，否则就留着当前的页面
		boolean configed = sp.getBoolean("configed", false);
		
		if(configed){
			//如果做过设置向导，就停留在手机防盗页面
			setContentView(R.layout.activity_lost_find);		
			
			//得到安全号码的控件
			tv_safe_number=(TextView)findViewById(R.id.tv_safe_number);
			iv_protecting=(ImageView)findViewById(R.id.iv_protecting);
			
			
			//得到安全号码,设置
			String safenumber=sp.getString("safenumber", "");
			tv_safe_number.setText(safenumber);
			
			//设置防盗状态
			Boolean protecting=sp.getBoolean("protecting", false);
			if(protecting){
				//开启保护
				iv_protecting.setImageResource(R.drawable.lock);
				
			}else{
				//未开启保护
				iv_protecting.setImageResource(R.drawable.unlock);
				
			}
			
			
		}else{
			//没做过设置向导,跳到设置向导
			Intent intent=new Intent(this,setup1Activity.class);
			startActivity(intent);
			//关闭当前页面
			finish();
		}
		
	}
	//重新进入手机防盗页面
	public void reEnterSetup(View View){
		Intent intent=new Intent(this,setup1Activity.class);
		startActivity(intent);
		finish();
	
	}
	
	
}
