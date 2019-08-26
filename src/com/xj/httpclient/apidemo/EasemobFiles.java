package com.xj.httpclient.apidemo;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.comm.Constants;
import com.xj.httpclient.utils.HTTPClientUtils;
import com.xj.httpclient.utils.TokenUtils;
import com.xj.httpclient.vo.ClientSecretCredential;
import com.xj.httpclient.vo.Credential;

public class EasemobFiles {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EasemobFiles.class); 
	private static final String APPKEY = Constants.APPKEY; 
	private static JsonNodeFactory factory = JsonNodeFactory.instance; 
	
	
	// 通过app的client_id和client_secret来获取app管理员token
	private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET, Constants.USER_ROLE_APPADMIN);
	
	private static Credential getCredential(){
		TokenUtils tokenUtils = new TokenUtils();
		String token = tokenUtils.getToken();
		if("0".equals(token)){
			LOGGER.info("token需要做首次请求操作");
		}else if("-1".equals(token)){
			LOGGER.info("token已超时并且被删除需重新请求");
		}else{
			return credential;
		}
		return credential;
	}

	/** 
	 * 图片语音文件下载 
	 *  
	 *  
	 * @param fileUUID 
	 *            文件在DB的UUID 
	 * @param shareSecret 
	 *            文件在DB中保存的shareSecret 
	 * @param localPath 
	 *            下载后文件存放地址 
	 * @param isThumbnail 
	 *            是否下载缩略图 true:缩略图 false:非缩略图 
	 * @return 
	 */ 
	public static ObjectNode mediaDownload(String fileUUID, String shareSecret, File localPath, Boolean isThumbnail) { 
		Credential thisCredential = getCredential();
		ObjectNode objectNode = factory.objectNode(); 
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) { 
			LOGGER.error("Bad format of Appkey: " + APPKEY); 
			objectNode.put("message", "Bad format of Appkey"); 
			return objectNode; 
		} 

		try { 
			List<NameValuePair> headers = new ArrayList<NameValuePair>(); 
			if (!StringUtils.isEmpty(shareSecret)) { 
				headers.add(new BasicNameValuePair("share-secret", shareSecret)); 
			} 
			headers.add(new BasicNameValuePair("Accept", "application/octet-stream")); 
			if (isThumbnail != null && isThumbnail) { 
				headers.add(new BasicNameValuePair("thumbnail", String.valueOf(isThumbnail))); 
			} 

			URL mediaDownloadUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatfiles/" + fileUUID); 
			HTTPClientUtils.downLoadFile(mediaDownloadUrl, thisCredential, headers, localPath); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 

		objectNode.put("message", "File download successfully .");
		return objectNode; 
	}
}