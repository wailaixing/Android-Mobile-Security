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
	private String title;
	private String desc_on;
	private String desc_off;
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

	//带有两个参数的构造方法,
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniView(context);
		title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.wailaixing.mobilesafe","title");
		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.wailaixing.mobilesafe","desc_on");
		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.wailaixing.mobilesafe","desc_off");
		tv_title.setText(title);
		setDesc(desc_off);
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
		if(checked){
			setDesc(desc_on);
		}else{
			setDesc(desc_off);
		}
		
		cb_status.setChecked(checked);
	}
	
	//设置组合控件的描述信息
	public void setDesc(String text){
		tv_desc.setText(text);
	}
}
