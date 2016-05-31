package com.wailaixing.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NumberAddressQueryUtils {
	private static final String TAG = "NumberAddressQueryUtils";
	private static String path="/data/data/com.wailaixing.mobilesafe/files/address.db";
	
	//传一个号码来，返回归属地
	public static String queryNumber(String number){
		
		String address=number;
		//path 把address.db这个数据库拷贝到data/data<包名>下的files/addresss.db
		
		
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		
		Log.i(TAG, "建立游标前");
		
		Cursor cursor=db.rawQuery(
				"select location from data2 where id = (select outkey from data1 where id = ?)",new String[] { number.substring(0, 7)});

		
		Log.i(TAG,"建立游标后");
		while (cursor.moveToNext()) {
			
			String location=cursor.getString(0);
			address=location;
			
		}
		cursor.close();
		
		return address;
	}
	
	
}
