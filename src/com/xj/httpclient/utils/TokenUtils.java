package com.xj.httpclient.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;


public class TokenUtils {
	
	private  final String fileName = "MyTokenFile";
	
	//private URL base = ClassLoader.getSystemResource("");
	
	public void saveFileToken(String token ,Long expDate){

		try {
		
			File file = new File(getMyPath()+"/"+fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			Map<String,Object> map = new HashMap<String, Object>();
			Long time = System.currentTimeMillis();
			map.put("token", token);
			map.put("time", time);
			map.put("expDate", expDate);
			out.writeObject(map);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  boolean fileIsEmpty(){
		File file = new File(getMyPath()+"/"+fileName);
		return file.exists();
	}
	
	
	// 0 - 还没有当前文件请求创建 并提示首次获取   -1 token 过期已被删除请求创建  其他 为真实token
	public  String getToken(){
		return getTokenWithFile();
	}
	
	@SuppressWarnings("unchecked")
	private String getTokenWithFile(){
		String result = "";
		File file = new File(getMyPath()+"/"+fileName);
		if(file.exists()){
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(new FileInputStream(file));
				Map<String, Object> map = (Map<String, Object>)in.readObject();
				Long time = (Long)map.get("time");
				Long thisTime = System.currentTimeMillis();
				if(thisTime - time > 3600*24*6*1000l){
					file.delete();
					result = "-1";
				}else{
					result = (String) map.get("token");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "-1";
			} catch (IOException e) {
				e.printStackTrace();
				return "-1";
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return "-1";
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			result = "0";
		}
		return result;
	}
	public static void main(String[] args) {
	
		TokenUtils t = new TokenUtils();
		t.getTokenWithFile();
		System.out.println(t.getToken());
	}
	
	
	
	private  String getMyPath(){
		
		String path = System.getProperty("user.dir");
		
		return path;
	}
}
