package com.xj.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Util {
	/**
	 * 32位小写
	 * @param str
	 * @return
	 */
	
	 private static final int min = 33;
	 
	 private static final int max = 126;
	 
	 private static final int sum = 8;
	 
	 public static String getMD5Str(String str) {  
         MessageDigest messageDigest = null;  
  
        try {  
             messageDigest = MessageDigest.getInstance("MD5");  
  
             messageDigest.reset();  
  
             messageDigest.update(str.getBytes("UTF-8"));  
         } catch (NoSuchAlgorithmException e) {  
             System.out.println("NoSuchAlgorithmException caught!");  
             System.exit(-1);  
         } catch (UnsupportedEncodingException e) {  
             e.printStackTrace();  
         }  
  
        byte[] byteArray = messageDigest.digest();  
  
         StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                 md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                 md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
         }  
  
        return md5StrBuff.toString();  
     }  
	 
	 public static void main(String[] args) {
		 
		System.out.println(System.currentTimeMillis() / 1000);
//		
//		for(;;){
//			System.out.println(getFcoude());
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	 }
	 
	 public static String getStr(){
		 Random r = new Random();
		 int num = 0;
		 char result[] = new char[sum];
		 for(int i = 0 ; i < sum ; i++){
			 num = r.nextInt(max-min)+min;
			 result[i] = (char) num;
		 }
		 return new String(result);
	 }
	 
	 public static String getFcoude(){
		 Random r = new Random();
		 int num = 0;
		 char result[] = new char[sum];
		 int type = 0;
		 for(int i = 0 ; i < sum ; i++){
			 type = r.nextInt(2)+1;
			 if(type == 1){
				 num = r.nextInt(10)+48;
			 }else{
				 num = r.nextInt(26)+65;
			 }
			
			 result[i] = (char) num;
		 }
		 return new String(result);
	 }
	
}
