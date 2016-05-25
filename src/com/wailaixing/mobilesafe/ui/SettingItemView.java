package com.wailaixing.mobilesafe.ui;

import com.wailaixing.mobilesafe.R;
import com.wailaixing.mobilesafe.SettingActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

//�Զ������Ͽؼ�,������2��textview��һ��checkbox��һ��view
public class SettingItemView extends RelativeLayout {

	private TextView tv_desc;
	private CheckBox cb_status;
	private TextView tv_title;
	private String title;
	private String desc_on;
	private String desc_off;
	//��ʼ�������ļ�
	private void iniView(Context context) {
		// TODO Auto-generated method stub
		//��һ�������ļ�-->view,���Ҽ�����SettingItemView
		View.inflate(context, R.layout.setting_item_view, this);//���ز����ļ�
		cb_status=(CheckBox)this.findViewById(R.id.cb_status);
		tv_desc=(TextView)this.findViewById(R.id.tv_desc);
		tv_title=(TextView)this.findViewById(R.id.tv_title);
		
	}
	
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		iniView(context);
	}

	//�������������Ĺ��췽��,
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
	
	//У����Ͽؼ��Ƿ�ѡ��
	public boolean isCheck(){
		return cb_status.isChecked();
	}
	
	//������Ͽؼ�״̬
	public void setChecked(boolean checked){
		if(checked){
			setDesc(desc_on);
		}else{
			setDesc(desc_off);
		}
		
		cb_status.setChecked(checked);
	}
	
	//������Ͽؼ���������Ϣ
	public void setDesc(String text){
		tv_desc.setText(text);
	}
}
