package com.wailaixing.mobilesafe;


import com.wailaixing.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	protected static final String TAG = "HomeActivity";
	private GridView list_home;
	private MyAdapter adapter;
	private SharedPreferences sp;

	private static String [] names = {
		"手机防盗","通讯卫士","软件管理",
		"进程管理","流量统计","手机杀毒",
		"缓存清理","高级工具","设置中心"
	};

	private static int[] ids={
			R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
			R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
			R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		list_home = (GridView) findViewById(R.id.list_home);
		adapter = new MyAdapter();
		list_home.setAdapter(adapter);
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 8://进入设置中心
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(intent);
					break;
				case 0://进入手机防盗页面
					showLostFindDialog();
					break;
				case 7://进入高级工具
					Intent intent_s=new Intent(HomeActivity.this,AtoolsActivity.class);
					startActivity(intent_s);
					break;
				default:
					break;
				}
			}
		});
	}


	protected void showLostFindDialog() {
		// TODO Auto-generated method stub
		//判断是否设置了密码
		if(isSetupPwd()){
			//设置过密码,输入框
			showEnterDialog();
			
		}else{
			//未设置密码，密码设置框
			showSetupPwdDialog();
		}

	}

	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancle;
	private AlertDialog dialog;

	//未设置密码，密码设置框
	private void showSetupPwdDialog() {
		// TODO Auto-generated method stub
		//对话框
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//自定义布局文件

		View view=View.inflate(HomeActivity.this, R.layout.dialog_setup_password, null);
		et_setup_pwd=(EditText)view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm=(EditText)view.findViewById(R.id.et_setup_confirm);
		ok=(Button)view.findViewById(R.id.ok);
		cancle=(Button)view.findViewById(R.id.cancle);

		//设置点击cancle的响应的事件
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//取消这个对话框
				dialog.dismiss();
			}
		});

		//设置点击ok的响应的事件
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//取出
				String password=et_setup_pwd.getText().toString().trim();//trim()去空格
				String confirm=et_setup_confirm.getText().toString().trim();//trim()去空格
				if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm)){
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
					return;
				}
					//判断是否一致，然后保存
				if(password.equals(confirm)){
						//消去对话框，进入手机防盗页面
					Editor editor=sp.edit();
					editor.putString("password",MD5Utils.md5P(password));//保存加密后的密码

					editor.commit();

					dialog.dismiss();

					Log.i(TAG,"一致，进入防盗页面");
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
				}else{
					//不一致，就提示
					//Toast.makeText(HomeActivity.this ,"两次输入不一致", 0).show();
					return ;
				}
				}
		});
		
		dialog=builder.create();
		dialog.setView(view,0,0,0,0);
		dialog.show();
	}

	//设置过密码,输入框
	private void showEnterDialog() {
		// TODO Auto-generated method stub
		//对话框
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//自定义布局文件

		View view=View.inflate(HomeActivity.this, R.layout.dialog_enter_password, null);
		et_setup_pwd=(EditText)view.findViewById(R.id.et_setup_pwd);
		//et_setup_confirm=(EditText)view.findViewById(R.id.et_setup_confirm);
		ok=(Button)view.findViewById(R.id.ok);
		cancle=(Button)view.findViewById(R.id.cancle);

		//设置点击cancle的响应的事件
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//取消这个对话框
				dialog.dismiss();
			}
		});

		//设置点击ok的响应的事件
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//取出密码
				String password=et_setup_pwd.getText().toString().trim();

				String savePassword=sp.getString("password", "");//取出加密后的文件
				if(TextUtils.isEmpty(password)){
					Toast.makeText(HomeActivity.this, "密码不可为空", 0).show();
					return ;
				}
				if(MD5Utils.md5P(password).equals(savePassword)){ //判断输入的密码经过加密是否等于
					//输入密码正确
					//关闭对话框
					dialog.dismiss();
					Log.i(TAG, "消去对话框，进入防盗页面");
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);

				}else{
					Toast.makeText(HomeActivity.this, "密码不正确", 0).show();
					et_setup_pwd.setText("");
					//密码不正确
					return;
				}
			}
		});
		//builder.setView(view);
		//注意 ！！！		
		dialog=builder.create();
		dialog.setView(view,0,0,0,0);
		dialog.show();
	}

	private boolean isSetupPwd(){
		String password=sp.getString("password",null);
		/*
		if(TextUtils.isEmpty(password)){
			return false;

		}else{
			return true;
		}
		*/
		return (! TextUtils.isEmpty(password));

	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(HomeActivity.this, R.layout.list_item_home, null);
			ImageView iv_item=(ImageView)view.findViewById(R.id.iv_item);
			TextView tv_item=(TextView)view.findViewById(R.id.tv_item);
			tv_item.setText(names[position]);
			iv_item.setImageResource(ids[position]);
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}