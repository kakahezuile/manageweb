package com.xj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";

	public static HttpURLConnection getHttpConnection(String url, String type, String token) {
		URL uri = null;
		HttpURLConnection con = null;
		try {
			uri = new URL(url);
			con = (HttpURLConnection) uri.openConnection();
			con.setRequestMethod(type); // type: POST, PUT, DELETE, GET
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			if (token != null && "" != token) {
				con.setRequestProperty("Authorization", "Bearer "+ token);
			}
			
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setConnectTimeout(6000); 
			con.setReadTimeout(6000); 
		} catch (Exception e) {
			logger.error(e);
		}
		return con;
	}

	/**
	 * 向指定URL发送type方法的请求
	 * 
	 * @param url 发送请求的URL
	 * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @param type type: POST, PUT, DELETE, GET
	 * @return URL 所代表远程资源的响应
	 */
	public static String sendByType(String url, String params,String token, String type) {
		String result = "";
		PrintWriter out = null;
		BufferedReader in = null;
		HttpURLConnection con = null;
		try {
			
			con=getHttpConnection(url, type, token);
			
			if (params != null && "" != null) {
				out = new PrintWriter(con.getOutputStream()); // 获取URLConnection对象对应的输出流
				out.print(params);// 发送请求参数
				out.flush();// flush输出流的缓冲
			}
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));// 定义BufferedReader输入流来读取URL的响应
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			logger.error(e);
		} finally { // 使用finally块来关闭输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error(ex);
			}
		}
		return result;
	}

	public static String httpUrl(String url){
		//String url="http://115.28.73.37:9090/api/V1/communities/1/users/statistics/"+startTimeInt+"/"+endTimeInt;
		//POST的URL
		HttpPost httppost=new HttpPost(url);
		//建立HttpPost对象
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		//建立一个NameValuePair数组，用于存储欲传送的参数
		//添加参数
		String result="";
		try (DefaultHttpClient client = new DefaultHttpClient()) {
			httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//设置编码
			HttpResponse response=client.execute(httppost);
			//发送Post,并返回一个HttpResponse对象
			if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
				result=EntityUtils.toString(response.getEntity());
			}
			//得到返回的字符串
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
