package com.wailaixing.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	//MD5����
	public static String md5P(String password){
			StringBuffer buffer;
			try {
				//�õ�һ����ϢժҪ��
				MessageDigest digest=MessageDigest.getInstance("md5");

				byte[] result=digest.digest(password.getBytes());
				buffer = new StringBuffer();
				
				//��byte��һ��������		
				for(byte b:result){
					//������
					int number=b & 0xff;
					String str=Integer.toHexString(number);
					
					if(str.length()==1){
						buffer.append("0");				
						
					}
					buffer.append(str);
				}
				//��׼��MD5���ܺ�Ľ��
				System.out.println(buffer.toString());
				return buffer.toString();				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
	}		
}

