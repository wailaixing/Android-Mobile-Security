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
		tv_splash_version.setText("版本号"+getVersionName());
		tv_update_info=(TextView)findViewById(R.id.tv_update_info);
		//得到是否升级
		boolean update=sp.getBoolean("update", false);
		if(update){
			//检查升级
			checkUpdate(); 			
		}else{
			//自动升级已经关闭
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
			case SHOW_UPDATE_DIALOG: //显示升级的对话框
				Log.i(TAG, "显示升级对话框");
				showUpdateDialog();
				break;
			case ENTER_HONE://进入主页面
				enterhome();//进入主页面
				break;
			case URL_ERROR://url错误
				enterhome();//进入主页面
				Toast.makeText(getApplicationContext(),"url错误",0).show();
				break;
			case NETWORK_ERROR://网络异常
				enterhome();//进入主页面
				Toast.makeText(getApplicationContext(),"服务器连接异常",0).show();				
				break;
			case JSON_ERROR://json解析出错
				enterhome();//进入主页面
				Toast.makeText(SplashActivity.this,"json解析出错",0).show();
				break;
			default:
				break;
			}
		}

		//弹出升级对话框
		protected void showUpdateDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new Builder(SplashActivity.this);
			builder.setTitle("提示升级");
			//builder.setCancelable(false);//点击返回就不起作用,强制升级
			builder.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					//点击返回进入主页面
					enterhome();
					dialog.dismiss();
				}
			});
			builder.setMessage(description);
			builder.setPositiveButton("立刻升级", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					//下载apk,并且替换安装
					if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
						//sd卡存在
						//afinal
						FinalHttp finalhttp=new FinalHttp();
						finalhttp.download(apkurl, Environment.getExternalStorageDirectory()
								.getAbsolutePath()+"/mobilesafe2.0.apk",
								new AjaxCallBack<File>() {

									@Override
									public void onFailure(Throwable t, int errorNo, String strMsg) {
										// TODO Auto-generated method stub
										t.printStackTrace();
										Toast.makeText(getApplicationContext(), "下载失败", 1).show();
										super.onFailure(t, errorNo, strMsg);
									}

									@Override
									public void onLoading(long count, long current) {
										// TODO Auto-generated method stub
										super.onLoading(count, current);
										tv_update_info.setVisibility(View.VISIBLE);
										//当前下载的百分比
										int progress=(int)(current * 100 / count);
										tv_update_info.setText("下载进度:"+progress+"%");
									}

									@Override
									public void onSuccess(File t) {
										// TODO Auto-generated method stub
										super.onSuccess(t); 
										installAPK(t);
										
									}
									private void installAPK(File t){
										//调用系统调用
										Intent intent =new Intent();
										intent.setAction("android.intent.action.VIEW");
										intent.addCategory("android.intent.category.DEFAULT");
										intent.setDataAndType(Uri.fromFile(t),"application/vnd.android.package-archive");
										
										startActivity(intent);
									}
								 
							});
					}else{
						Toast.makeText(getApplicationContext(), "没有SD卡", 0).show();
						return ;
					}
				}
			});
			builder.setNegativeButton("下次再说", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					enterhome();//进入主页面
				}
			});
			
			builder.show();
		}
		
	};
	
	
	protected void enterhome(){
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);//关闭当前页面
		finish();
	}
	
	/**
	*检查是否有新版本，如果有就升级
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
						//联网
						HttpURLConnection conn=(HttpURLConnection)url.openConnection();
						conn.setRequestMethod("Get");//设置请求方式
						conn.setConnectTimeout(4000);//设置超时时间
						int code=conn.getResponseCode();//设置响应码
						if(code==200){
							//链接成功
							InputStream is=conn.getInputStream();
							//将流转换成String
							String result=StreamTools.readFromStream(is);
							Log.i(TAG, "联网成功"+result);
							
							//json解析
						    JSONObject obj=new JSONObject(result);
						    //得到服务器的版本信息
							String version=(String)obj.get("version");
							description=(String)obj.get("description");							
							apkurl = (String)obj.get("apkurl");
							
							//校验新版本
							if(getVersionName().equals(version)){
								//版本一致没有新版本,进入主页面
								mes.what=ENTER_HONE;								
							}else{
								//有新版本,升级
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
					//花的时间
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



	//得到版本名
	private String getVersionName(){
//		用来管理手机的apk
		PackageManager pm=getPackageManager();
		try {
			//得到指定apk的功能清单文件
			PackageInfo info=pm.getPackageInfo("com.wailaixing.mobilesafe", 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
