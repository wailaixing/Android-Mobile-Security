package com.wailaixing.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	//MD5加密
	public static String md5P(String password){
			StringBuffer buffer;
			try {
				//得到一个信息摘要器
				MessageDigest digest=MessageDigest.getInstance("md5");

				byte[] result=digest.digest(password.getBytes());
				buffer = new StringBuffer();
				
				//将byte做一个与运算		
				for(byte b:result){
					//与运算
					int number=b & 0xff;
					String str=Integer.toHexString(number);
					
					if(str.length()==1){
						buffer.append("0");				
						
					}
					buffer.append(str);
				}
				//标准的MD5加密后的结果
				System.out.println(buffer.toString());
				return buffer.toString();				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
	}		
}

