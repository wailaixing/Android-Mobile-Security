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
		"�ֻ�����","ͨѶ��ʿ","�������",
		"���̹���","����ͳ��","�ֻ�ɱ��",
		"��������","�߼�����","��������"
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
				case 8://������������
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(intent);
					break;
				case 0://�����ֻ�����ҳ��
					showLostFindDialog();
					break;
				case 7://����߼�����
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
		//�ж��Ƿ�����������
		if(isSetupPwd()){
			//���ù�����,�����
			showEnterDialog();
			
		}else{
			//δ�������룬�������ÿ�
			showSetupPwdDialog();
		}

	}

	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancle;
	private AlertDialog dialog;

	//δ�������룬�������ÿ�
	private void showSetupPwdDialog() {
		// TODO Auto-generated method stub
		//�Ի���
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//�Զ��岼���ļ�

		View view=View.inflate(HomeActivity.this, R.layout.dialog_setup_password, null);
		et_setup_pwd=(EditText)view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm=(EditText)view.findViewById(R.id.et_setup_confirm);
		ok=(Button)view.findViewById(R.id.ok);
		cancle=(Button)view.findViewById(R.id.cancle);

		//���õ��cancle����Ӧ���¼�
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//ȡ������Ի���
				dialog.dismiss();
			}
		});

		//���õ��ok����Ӧ���¼�
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ȡ��
				String password=et_setup_pwd.getText().toString().trim();//trim()ȥ�ո�
				String confirm=et_setup_confirm.getText().toString().trim();//trim()ȥ�ո�
				if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm)){
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
					return;
				}
					//�ж��Ƿ�һ�£�Ȼ�󱣴�
				if(password.equals(confirm)){
						//��ȥ�Ի��򣬽����ֻ�����ҳ��
					Editor editor=sp.edit();
					editor.putString("password",MD5Utils.md5P(password));//������ܺ������

					editor.commit();

					dialog.dismiss();

					Log.i(TAG,"һ�£��������ҳ��");
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
				}else{
					//��һ�£�����ʾ
					//Toast.makeText(HomeActivity.this ,"�������벻һ��", 0).show();
					return ;
				}
				}
		});
		
		dialog=builder.create();
		dialog.setView(view,0,0,0,0);
		dialog.show();
	}

	//���ù�����,�����
	private void showEnterDialog() {
		// TODO Auto-generated method stub
		//�Ի���
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//�Զ��岼���ļ�

		View view=View.inflate(HomeActivity.this, R.layout.dialog_enter_password, null);
		et_setup_pwd=(EditText)view.findViewById(R.id.et_setup_pwd);
		//et_setup_confirm=(EditText)view.findViewById(R.id.et_setup_confirm);
		ok=(Button)view.findViewById(R.id.ok);
		cancle=(Button)view.findViewById(R.id.cancle);

		//���õ��cancle����Ӧ���¼�
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//ȡ������Ի���
				dialog.dismiss();
			}
		});

		//���õ��ok����Ӧ���¼�
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ȡ������
				String password=et_setup_pwd.getText().toString().trim();

				String savePassword=sp.getString("password", "");//ȡ�����ܺ���ļ�
				if(TextUtils.isEmpty(password)){
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
					return ;
				}
				if(MD5Utils.md5P(password).equals(savePassword)){ //�ж���������뾭�������Ƿ����
					//����������ȷ
					//�رնԻ���
					dialog.dismiss();
					Log.i(TAG, "��ȥ�Ի��򣬽������ҳ��");
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);

				}else{
					Toast.makeText(HomeActivity.this, "���벻��ȷ", 0).show();
					et_setup_pwd.setText("");
					//���벻��ȷ
					return;
				}
			}
		});
		//builder.setView(view);
		//ע�� ������		
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