package com.wailaixing.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.IncompleteAnnotationException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.StreamHandler;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.wailaixing.mobilesafe.utils.StreamTools;

import android.R.attr;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";
	protected static final int SHOW_UPDATE_DIALOG = 0;	
	protected static final int ENTER_HONE = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	
	private TextView tv_splash_version;
	private String description;//
	private String apkurl;//
	private TextView tv_update_info;
	private SharedPreferences sp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		sp=getSharedPreferences("config", MODE_PRIVATE);
		
		tv_splash_version=(TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("�汾��"+getVersionName());
		tv_update_info=(TextView)findViewById(R.id.tv_update_info);
		//�õ��Ƿ�����
		boolean update=sp.getBoolean("update", false);
		if(update){
			//�������
			checkUpdate(); 			
		}else{
			//�Զ������Ѿ��ر�
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					enterhome();
				}
			},2000);

		}
		
		AlphaAnimation aa=new AlphaAnimation(0.2f,1.0f);
		aa.setDuration(500);
		findViewById(R.id.rl_root_splash).startAnimation(aa);
	}

	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case SHOW_UPDATE_DIALOG: //��ʾ�����ĶԻ���
				Log.i(TAG, "��ʾ�����Ի���");
				showUpdateDialog();
				break;
			case ENTER_HONE://������ҳ��
				enterhome();//������ҳ��
				break;
			case URL_ERROR://url����
				enterhome();//������ҳ��
				Toast.makeText(getApplicationContext(),"url����",0).show();
				break;
			case NETWORK_ERROR://�����쳣
				enterhome();//������ҳ��
				Toast.makeText(getApplicationContext(),"�����������쳣",0).show();				
				break;
			case JSON_ERROR://json��������
				enterhome();//������ҳ��
				Toast.makeText(SplashActivity.this,"json��������",0).show();
				break;
			default:
				break;
			}
		}

		//���������Ի���
		protected void showUpdateDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new Builder(SplashActivity.this);
			builder.setTitle("��ʾ����");
			//builder.setCancelable(false);//������ؾͲ�������,ǿ������
			builder.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					//������ؽ�����ҳ��
					enterhome();
					dialog.dismiss();
				}
			});
			builder.setMessage(description);
			builder.setPositiveButton("��������", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					//����apk,�����滻��װ
					if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
						//sd������
						//afinal
						FinalHttp finalhttp=new FinalHttp();
						finalhttp.download(apkurl, Environment.getExternalStorageDirectory()
								.getAbsolutePath()+"/mobilesafe2.0.apk",
								new AjaxCallBack<File>() {

									@Override
									public void onFailure(Throwable t, int errorNo, String strMsg) {
										// TODO Auto-generated method stub
										t.printStackTrace();
										Toast.makeText(getApplicationContext(), "����ʧ��", 1).show();
										super.onFailure(t, errorNo, strMsg);
									}

									@Override
									public void onLoading(long count, long current) {
										// TODO Auto-generated method stub
										super.onLoading(count, current);
										tv_update_info.setVisibility(View.VISIBLE);
										//��ǰ���صİٷֱ�
										int progress=(int)(current * 100 / count);
										tv_update_info.setText("���ؽ���:"+progress+"%");
									}

									@Override
									public void onSuccess(File t) {
										// TODO Auto-generated method stub
										super.onSuccess(t); 
										installAPK(t);
										
									}
									private void installAPK(File t){
										//����ϵͳ����
										Intent intent =new Intent();
										intent.setAction("android.intent.action.VIEW");
										intent.addCategory("android.intent.category.DEFAULT");
										intent.setDataAndType(Uri.fromFile(t),"application/vnd.android.package-archive");
										
										startActivity(intent);
									}
								 
							});
					}else{
						Toast.makeText(getApplicationContext(), "û��SD��", 0).show();
						return ;
					}
				}
			});
			builder.setNegativeButton("�´���˵", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					enterhome();//������ҳ��
				}
			});
			
			builder.show();
		}
		
	};
	
	
	protected void enterhome(){
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);//�رյ�ǰҳ��
		finish();
	}
	
	/**
	*����Ƿ����°汾������о�����
	*/
	private void checkUpdate() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run(){
				long starttime=System.currentTimeMillis();
				Message mes=Message.obtain();
				//URL http://192.168.155.1:8080/updateinfo.html
				try {
						URL url=new URL(getString(R.string.serverurl));
						//����
						HttpURLConnection conn=(HttpURLConnection)url.openConnection();
						conn.setRequestMethod("Get");//��������ʽ
						conn.setConnectTimeout(4000);//���ó�ʱʱ��
						int code=conn.getResponseCode();//������Ӧ��
						if(code==200){
							//���ӳɹ�
							InputStream is=conn.getInputStream();
							//����ת����String
							String result=StreamTools.readFromStream(is);
							Log.i(TAG, "�����ɹ�"+result);
							
							//json����
						    JSONObject obj=new JSONObject(result);
						    //�õ��������İ汾��Ϣ
							String version=(String)obj.get("version");
							description=(String)obj.get("description");							
							apkurl = (String)obj.get("apkurl");
							
							//У���°汾
							if(getVersionName().equals(version)){
								//�汾һ��û���°汾,������ҳ��
								mes.what=ENTER_HONE;								
							}else{
								//���°汾,����
								mes.what=SHOW_UPDATE_DIALOG;		
							}
						}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					mes.what=URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
				// TODO Auto-generated catch block
					mes.what=NETWORK_ERROR;
					e.printStackTrace();
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					mes.what=JSON_ERROR;
					e.printStackTrace(); 
				}finally{
					long endtime=System.currentTimeMillis();
					//����ʱ��
					long dTime=endtime-starttime;
					if(dTime<2000){
						try {
							Thread.sleep(2000-dTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					handler.sendMessage(mes);
				}
				
			};
		}.start();
	}



	//�õ��汾��
	private String getVersionName(){
//		���������ֻ���apk
		PackageManager pm=getPackageManager();
		try {
			//�õ�ָ��apk�Ĺ����嵥�ļ�
			PackageInfo info=pm.getPackageInfo("com.wailaixing.mobilesafe", 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
