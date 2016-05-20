package com.wailaixing.mobilesafe.ui;

import com.wailaixing.mobilesafe.R;
import com.wailaixing.mobilesafe.SettingActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

//自定义的组合控件,里面有2个textview，一个checkbox，一个view
public class SettingItemView extends RelativeLayout {

	private TextView tv_desc;
	private CheckBox cb_status;
	private TextView tv_title;
	//初始化布局文件
	private void iniView(Context context) {
		// TODO Auto-generated method stub
		//把一个布局文件-->view,并且加载在SettingItemView
		View.inflate(context, R.layout.setting_item_view, this);//加载布局文件
		cb_status=(CheckBox)this.findViewById(R.id.cb_status);
		tv_desc=(TextView)this.findViewById(R.id.tv_desc);
		tv_title=(TextView)this.findViewById(R.id.tv_title);
		
	}
	
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		iniView(context);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniView(context);
	}

	public SettingItemView(Context context) {
		super(context);
		iniView(context);
	}
	
	//校验组合控件是否选中
	public boolean isCheck(){
		return cb_status.isChecked();
	}
	
	//设置组合控件状态
	public void setChecked(boolean checked){
		cb_status.setChecked(checked);
	}
	
	//设置组合控件的描述信息
	public void setDesc(String text){
		tv_desc.setText(text);
	}
}
