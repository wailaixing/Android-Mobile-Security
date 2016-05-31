package com.wailaixing.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Toast;

public abstract class BaseSetupActivity extends Activity {
	//1����һ������ʶ����
	private GestureDetector detector;
	protected SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//2ʵ��������ʶ����
		detector =new GestureDetector(this,new  OnGestureListener(){
			//����ָ�����滬�����ص�
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				
				//����б�Ż���
				if(Math.abs(e2.getRawY()-e1.getRawY())>100){
					Toast.makeText(BaseSetupActivity.this, "����б�Ż���", 0).show();
					return true;
				}
				
				
				//������X�Ử�����������
				if(Math.abs(velocityX)<200){
					Toast.makeText(BaseSetupActivity.this, "��������̫��", 0).show();
					return true;
				}
				
				
				if((e2.getRawX()-e1.getRawX())>200){
					//��ʾ��һ��ҳ��,��������
					System.out.println("��ʾ��һ��ҳ��,��������");
					showPre();
					return true;
					
				}
				
				if((e1.getRawX()-e2.getRawX())>200){
					//��ʾ��һ��ҳ��,��������
					System.out.println("��ʾ��һ��ҳ��,��������");
					showNext();
					return true;
				}
				return false;
			}


			@Override
			public boolean onDown(MotionEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onSingleTapUp(MotionEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});			
		
	}
	
	
	public abstract void showNext();
	public abstract void showPre();	
	
	//�����һ��
	public void next(View view){
		showNext();
	}

	//   ��һ��
	public void pre(View view){
		showPre();
	}
	
	
	//3 ʹ������ʶ����
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}		
		
	
}
