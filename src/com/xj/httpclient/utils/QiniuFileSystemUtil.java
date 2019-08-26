package com.xj.httpclient.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.net.Http;
import com.qiniu.api.resumableio.ResumeableIoApi;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;
import com.xj.bean.QiNiuBody;

/**
 * @author wanmin 2014-4-11 下午14:15:00
 * @version V1.0
 * @modify by reason:{方法名}:{原因}
 * @Description 实现七牛云存储上传下载等操作工具类
 */
public class QiniuFileSystemUtil{
	private static final Logger LOG = LoggerFactory.getLogger(QiniuFileSystemUtil.class);
	
	private static Mac mac = null;
	/**
	 * 云存储空间名
	 */
	private static String bucketName = "ltzmaxwell";
	
	/**
	 * 云存储域
	 */
	private static String domain = "";
	
	public static String myProject = "http://7d9lcl.com2.z0.glb.qiniucdn.com/";
	
	static {
		Config.ACCESS_KEY = "QxHhfGqml2EPtOT0qYokwTR6wrre6t4cUmbgodX8";
		Config.SECRET_KEY = "tzBCHAXrJBulNFV56jwW-rwUoXjHZNN0gB7-5KCR";
		bucketName = "ltzmaxwell";
		domain = "";
		mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	}
	
	/**
	 * 测试通过
	 * @Title: getUptoken 
	 * @Description: 获取上传token验证
	 * @param @return 
	 * @return String 
	 * @throws
	 */
	public static String getUptoken(QiNiuBody qiNiuBody) {
		PutPolicy putPolicy = new PutPolicy(bucketName);
		
		if(qiNiuBody.getQiNiuId() != null && qiNiuBody.getQiNiuType() != null){
			putPolicy.callbackUrl = "http://wuye.ixiaojian.com/api/v1/myCallbackUrl";
//			putPolicy.callbackBody = "&name=$(key)&hash=$(etag)&qiNiuType="+qiNiuBody.getQiNiuType()+"&qiNiuId="+qiNiuBody.getQiNiuId();
			qiNiuBody.setKey("$(key)");
			qiNiuBody.setHash("$(etag)");
			putPolicy.callbackBody = new Gson().toJson(qiNiuBody);
		}
	
		String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
			LOG.error(e.getMessage());
		} catch (JSONException e) {
			LOG.error(e.getMessage());
		}
		return uptoken;
	}
	
	public static void delete(String fileName){
		RSClient rsClient = new RSClient(mac);
		rsClient.delete(bucketName, fileName);
	}

//	/**
//	 * 
//	 * @Title: upload 
//	 * @Description: 通过io流实现向七牛上传文件
//	 * @param @param io
//	 * @param @param path
//	 * @param @param id
//	 * @param @return
//	 * @param @throws Exception 
//	 * @return PutRet 
//	 * @throws
//	 */
	public static PutRet upload(File file , String fileName  ) throws Exception {
		
		LOG.info("七牛云存储上传");
		String uptoken = getUptoken(new QiNiuBody());
		if(null==uptoken) {
			LOG.error("七牛云存储上传token获取失败");
			throw new Exception("七牛云存储上传token获取失败");
		}
//		PutExtra extra = new PutExtra();
		PutRet ret = null;
		try {
//			ret = IoApi.Put(uptoken, id, io, extra);
			ret = ResumeableIoApi.put(file,uptoken,fileName);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception("七牛云存储上传文件失败");
		}
		LOG.info("七牛云存储上传--结束");
		return ret;
	}
	
	/**
	 * 
	 * @Title: scanFile 
	 * @Description: 通过文件id获取文件基本信息
	 * @param @param id
	 * @param @return
	 * @param @throws Exception 
	 * @return Entry 
	 * @throws
	 */
	public Entry scanFile(String id) throws Exception {
	
		RSClient client = new RSClient(mac);
		Entry statRet = null;
		try {
			statRet = client.stat(bucketName, id);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception("七牛云存储获取文件信息失败");
		}
		return statRet;
	}

	public String getDownUrl(String id) throws Exception {
	
		String baseUrl = URLUtils.makeBaseUrl(domain, id);
		GetPolicy getPolicy = new GetPolicy();
		String downloadUrl = "";
		try {
			downloadUrl = getPolicy.makeRequest(baseUrl, mac);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception("七牛云存储获取文件文件下载路径失败");
		}
		return downloadUrl;
	}
	
	/**
	 * 
	 * @Title: downFile 
	 * @Description: 通过id下载文件
	 * @param @throws Exception 
	 * @return void 
	 * @throws
	 */
	public void downFile(String id) throws Exception {

		String baseUrl;
		
		baseUrl = URLUtils.makeBaseUrl(domain, id);
		GetPolicy getPolicy = new GetPolicy();
		String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
		System.out.println(downloadUrl);
		download(downloadUrl,id);
	}
	
	private void download(String url , String id) throws ClientProtocolException, IOException {
		HttpGet httpget9 = new HttpGet(url);
		HttpClient client9 = Http.getClient();
		HttpResponse res = client9.execute(httpget9);
		Header[] hs = res.getAllHeaders();
		for (Header h : hs) {
			System.out.println(h);
			System.out.println(new String(h.getValue().getBytes("iso8859-1"), "utf-8"));
		}
		HttpEntity entity = res.getEntity();
		String contentType = entity.getContentType().toString();
		String type[] = contentType.split("/");
		int lengh = type.length;
		contentType = type[lengh-1];
		if (entity.isStreaming()) {
			InputStream is = entity.getContent();
			File f = new File(getMyPath()+"/qiniu");
			if(!f.exists() && !f.isDirectory()){
				f.mkdir();
			}
			try (FileOutputStream fo = new FileOutputStream(getMyPath()+"/qiniu/."+id+contentType);) {
				byte[] bs = new byte[1024 * 4];
				int len = -1;
				while ((len = is.read(bs)) != -1) {
					fo.write(bs, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void fileDownload(String url , String fileName) throws ClientProtocolException, IOException {
		System.out.println(url);
		HttpGet httpget9 = new HttpGet(url);
		HttpClient client9 = Http.getClient();
		HttpResponse res = client9.execute(httpget9);
		Header[] hs = res.getAllHeaders();
		for (Header h : hs) {
			System.out.println(h);
			System.out.println(new String(h.getValue().getBytes("iso8859-1"), "utf-8"));
		}
		HttpEntity entity = res.getEntity();
		String contentType = entity.getContentType().toString();
		String type[] = contentType.split("/");

		int lengh = type.length;
		contentType = type[lengh - 1];
		if (entity.isStreaming()) {
			InputStream is = entity.getContent();
			File f = new File(new QiniuFileSystemUtil().getMyPath() + "\\qiniu");
			if (!f.exists() && !f.isDirectory()) {
				f.mkdir();
			}
			FileOutputStream fo = new FileOutputStream(new QiniuFileSystemUtil().getMyPath() + "\\qiniu\\"+ fileName);
			byte[] bs = new byte[1024 * 4];
			int len = -1;
			while ((len = is.read(bs)) != -1) {
				fo.write(bs, 0, len);
			}
			fo.close();
		}
		

	}

	public void putFile(String uptoken, String key, FileInputStream io,
			PutExtra extra) throws Exception {
		if (extra.checkCrc == 1) {
			extra.crc32 = getCRC32(io);
		}
	}

	private long getCRC32(FileInputStream io) throws Exception {
		CRC32 crc32 = new CRC32();
		FileInputStream in = null;
		CheckedInputStream checkedInputStream = null;
		long crc = 0;
		try {
			in = io;
			checkedInputStream = new CheckedInputStream(in, crc32);
			while (checkedInputStream.read() != -1) {
			}
			crc = crc32.getValue();
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			if (checkedInputStream != null) {
				checkedInputStream.close();
				checkedInputStream = null;
			}
		}
		return crc;
	}

	public static String CHARSET = "utf-8";

	public static String encodeBase64URLSafeString(String p) {
		return encodeBase64URLSafeString(toByte(p));
	}

	public static String encodeBase64URLSafeString(byte[] binaryData) {
		byte[] b = encodeBase64URLSafe(binaryData);
		return toString(b);
	}

	/** 保留尾部的**/
	public static byte[] encodeBase64URLSafe(byte[] binaryData) {
		byte[] b = Base64.encodeBase64URLSafe(binaryData);
		int mod = b.length % 4;
		if (mod == 0) {
			return b;
		} else {
			int pad = 4 - mod;
			byte[] b2 = new byte[b.length + pad];
			System.arraycopy(b, 0, b2, 0, b.length);
			b2[b.length] = '=';
			if (pad > 1) {
				b2[b.length + 1] = '=';
			}
			return b2;
		}
	}

	public static byte[] toByte(String s) {
		try {
			return s.getBytes(CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(byte[] bs) {
		try {
			return new String(bs, CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String signRequest(HttpRequestBase request, String secretKey,
			String accessKey) throws NoSuchAlgorithmException,
			InvalidKeyException, IOException {
		URI uri = request.getURI();
		String path = uri.getRawPath();
		String query = uri.getRawQuery();
		byte[] sk = toByte(secretKey);
		javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
		SecretKeySpec keySpec = new SecretKeySpec(sk, "HmacSHA1");
		mac.init(keySpec);
		mac.update(toByte(path));
		if (query != null && query.length() != 0) {
			mac.update((byte) ('?'));
			mac.update(toByte(query));
		}
		mac.update((byte) '\n');
		signEntity(request, mac);
		byte[] digest = mac.doFinal();
		byte[] digestBase64 = encodeBase64URLSafe(digest);
		StringBuffer b = new StringBuffer();
		b.append(accessKey);
		b.append(':');
		b.append(toString(digestBase64));
		return b.toString();
	}

	private static void signEntity(HttpRequestBase request, javax.crypto.Mac mac)
			throws IOException {
		HttpEntity entity = getEntity(request);
		if (entity != null) {
			if (needSignEntity(entity, request)) {
				ByteArrayOutputStream w = new ByteArrayOutputStream();
				entity.writeTo(w);
				mac.update(w.toByteArray());
			}
		}
	}

	private static HttpEntity getEntity(HttpRequestBase request) {
		try {
			HttpPost post = (HttpPost) request;
			if (post != null) {
				return post.getEntity();
			}
		} catch (Exception e) {
		}
		return null;
	}

	private static boolean needSignEntity(HttpEntity entity,
			HttpRequestBase request) {
		String contentType = "application/x-www-form-urlencoded";
		Header ect = entity.getContentType();
		if (ect != null && contentType.equals(ect.getValue())) {
			return true;
		}
		Header[] cts = request.getHeaders("Content-Type");
		for (Header ct : cts) {
			if (contentType.equals(ct.getValue())) {
				return true;
			}
		}
		return false;
	}

	public static CallRet handleResult(HttpResponse response) {
		try {
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();
			String responseBody = EntityUtils.toString(response.getEntity(), CHARSET);
			return new CallRet(statusCode, responseBody);
		} catch (Exception e) {
			return new CallRet(400, e);
		}
	}
	private String getMyPath(){
		return this.getClass().getResource("").getPath();
	}
}
