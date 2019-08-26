package com.xj.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.xj.bean.Facilities;
import com.xj.bean.ShopItem;
import com.xj.bean.Shops;

public class clientTest {
	public static void main(String[] args) {
		String url = "http://localhost:8080/MyWebApp/api/v1/communities/1/shops/1/shopItems";
		ShopItem shopItem = new ShopItem();
		shopItem.setCreateTime(System.currentTimeMillis());
		shopItem.setIsUpdate(0);
		
		shopItem.setServiceName("123");
		shopItem.setShopId(1l);
		shopItem.setStatus("available");
		List<ShopItem> list = new ArrayList<ShopItem>();
		list.add(shopItem);
		System.out.println(list instanceof List);
		send(url, list, "POST");
//		sendGet(url);
//		Shops s = new Shops();
//		s.setAddress("!");
////		s.setCommunityId(1);
//		s.setCreateTime(System.currentTimeMillis() / 1000);
//		s.setDiscription("");
//		s.setEmobId("");
//		s.setLogo("");
//		s.setPhone("");
//		s.setShopId(1l);
//		s.setSort("1");
//		s.setStatus("1");
//    	s.setShopName("wogaide12344312312");
//		send(url, s, "POST");
//		Facilities facilities = new Facilities();
//		facilities.setAddress("");
//		facilities.setCommunityId(1);
//		Long time = System.currentTimeMillis() / 1000;
//		facilities.setCreateTime(time.intValue());
//		facilities.setDescription("123");
//		facilities.setFacilityId(1l);
//		facilities.setFacilityName("123");
//		facilities.setLatitude(123f);
//		facilities.setLogo("123");
//		facilities.setPhone("1243");
//		facilities.setSort("1");
//		facilities.setStatus("block");
//		facilities.setLongitude(123f);
//		send(url, new Object(), "GET");
	}
	@SuppressWarnings("unchecked")
	public static String send(String tourl , Object object , String method) {  
		  try {
	            //创建连接
	            URL url = new URL(tourl);
	            HttpURLConnection connection = (HttpURLConnection) url
	                    .openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setRequestMethod(method);
	            connection.setUseCaches(false);
	            connection.setInstanceFollowRedirects(true);
	            connection.setRequestProperty("Content-Type",
	                    "application/json");

	            connection.connect();

	            //POST请求
	            DataOutputStream out = new DataOutputStream(
	                    connection.getOutputStream());
	            Gson g = new Gson();
	            if(object instanceof List){
	            	out.writeBytes(g.toJson((List<ShopItem>)object));
//	            	Users my = new Users();
//	 	    		my.setUsername("1234");
//	 	    		my.setEmobId("123");
//	 	    		my.setPassword(MD5Util.getMD5Str("1234"));
//	 	    		my.setPhone("123");
//	 	    		my.setGender("f");
//	 	    		my.setAge(18);
//	 	    		my.setStatus("normal");
//	 	    		my.setRoom("123");
//	 	    		my.setCreateTime(123456);
//	 	    		my.setSalt("123");
//	 	    		 out.writeBytes(new Object());
	            }else if(object instanceof Shops){
	            	
	            	 out.writeBytes(g.toJson((Shops)object));
	            }else if(object instanceof Facilities){
	            	out.writeBytes(g.toJson((Facilities)object));
	            }
	    		
	           
	            out.flush();
	            out.close();

	            //读取响应
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String lines;
	            StringBuffer sb = new StringBuffer("");
	            while ((lines = reader.readLine()) != null) {
	                lines = new String(lines.getBytes(), "utf-8");
	                sb.append(lines);
	            }
	            System.out.println(sb);
	            reader.close();
	            // 断开连接
	            connection.disconnect();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        return null;
    }  
}
