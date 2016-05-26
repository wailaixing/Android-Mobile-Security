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
	//1定义一个手势识别器
	private GestureDetector detector;
	protected SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//2实例化手势识别器
		detector =new GestureDetector(this,new  OnGestureListener(){
			//当手指在上面滑动，回调
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				
				//屏蔽斜着滑动
				if(Math.abs(e2.getRawY()-e1.getRawY())>100){
					Toast.makeText(BaseSetupActivity.this, "不能斜着滑动", 0).show();
					return true;
				}
				
				
				//屏蔽在X轴滑动过慢的情况
				if(Math.abs(velocityX)<200){
					Toast.makeText(BaseSetupActivity.this, "滑动不可太慢", 0).show();
					return true;
				}
				
				
				if((e2.getRawX()-e1.getRawX())>200){
					//显示上一个页面,从左往右
					System.out.println("显示上一个页面,从左往右");
					showPre();
					return true;
					
				}
				
				if((e1.getRawX()-e2.getRawX())>200){
					//显示下一个页面,从右往左
					System.out.println("显示下一个页面,从右往左");
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
	
	//点击下一步
	public void next(View view){
		showNext();
	}

	//   上一步
	public void pre(View view){
		showPre();
	}
	
	
	//3 使用手势识别器
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}		
		
	
}
