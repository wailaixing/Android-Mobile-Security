package com.wailaixing.mobilesafe;

import java.text.Format;
import java.util.List;

import com.wailaixing.mobilesafe.engine.AppInfoProvider;
import com.wailaixing.mobilesafe.domain.AppInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.SelectionBoundsAdjuster;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AppManagerActivity extends Activity {

	private TextView tv_avail_rom;
	private TextView tv_avail_sd;
	
	private ListView lv_app_manager;
	private LinearLayout ll_loading;
	
	//
	private List<AppInfo> appInfos;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_manager);
		tv_avail_rom=(TextView) findViewById(R.id.tv_avail_rom);
		tv_avail_sd=(TextView) findViewById(R.id.tv_avail_sd);
		lv_app_manager=(ListView) findViewById(R.id.lv_app_manager);
		ll_loading=(LinearLayout) findViewById(R.id.ll_loading);
		
		
		long sdsize=getAvailSpace(Environment.getExternalStorageDirectory().getAbsolutePath());
		long romsize=getAvailSpace(Environment.getDataDirectory().getAbsolutePath());
		
		//
		tv_avail_sd.setText("SD卡可用空间: "+Formatter.formatFileSize(this, sdsize));
		tv_avail_rom.setText("内存可用空间: "+Formatter.formatFileSize(this, romsize));		
		
		
		ll_loading.setVisibility(View.VISIBLE);
		
		new Thread(){
			public void run(){
				appInfos=AppInfoProvider.getAppInfos(AppManagerActivity.this);
				//加载listview的数据适配器
				runOnUiThread( new Runnable() {
					public void run() {
						lv_app_manager.setAdapter(new AppManagerAdapter());
						ll_loading.setVisibility(View.VISIBLE);
					}
				});
				
				
			}
			
		}.start();
	}
	
	public class AppManagerAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
//			TextView tv=new TextView(getApplicationContext());
//			tv.setText(appInfos.get(position).toString());
//			return tv;
			View view;
			ViewHolder holder;
			if(convertView!=null){
				view=convertView;
				holder=(ViewHolder)view.getTag();
				
			}else{
				view=View.inflate(getApplicationContext(), R.layout.list_item_appinfo, null);
				
				holder=new ViewHolder();
				holder.iv_icon=(ImageView) view.findViewById(R.id.iv_app_icon);
				holder.tv_location=(TextView)view.findViewById(R.id.tv_app_location);
				holder.tv_name=(TextView)view.findViewById(R.id.tv_app_name);
				view.setTag(holder);
			}
			AppInfo appInfo=appInfos.get(position);
			holder.iv_icon.setImageDrawable(appInfo.getIcon());
			holder.tv_name.setText(appInfo.getName());
			return view;
		}
	}
	
	static class ViewHolder{
		TextView tv_name;
		TextView tv_location;
		ImageView iv_icon;
	}
	
	//获取某个目录的可用空间
	private long getAvailSpace(String path){
		StatFs statfs=new StatFs(path);
		statfs.getBlockCount();//获取分区的个数
		long size=statfs.getBlockSize();//获取分区的大小
		
		long count=statfs.getAvailableBlocks();//可用区块个数
		return size*count;
	
	}
}
