package com.wailaixing.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NumberAddressQueryUtils {
	private static final String TAG = "NumberAddressQueryUtils";
	private static String path="/data/data/com.wailaixing.mobilesafe/files/address.db";
	
	//��һ�������������ع�����
	public static String queryNumber(String number){
		
		String address=number;
		//path ��address.db������ݿ⿽����data/data<����>�µ�files/addresss.db
		
		
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		
		Log.i(TAG, "�����α�ǰ");
		
		Cursor cursor=db.rawQuery(
				"select location from data2 where id = (select outkey from data1 where id = ?)",new String[] { number.substring(0, 7)});

		
		Log.i(TAG,"�����α��");
		while (cursor.moveToNext()) {
			
			String location=cursor.getString(0);
			address=location;
			
		}
		cursor.close();
		
		return address;
	}
	
	
}
