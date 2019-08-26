package com.xj.httpclient.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.comm.Constants;
import com.xj.comm.HTTPMethod;
import com.xj.httpclient.vo.Credential;
import com.xj.httpclient.vo.Token;

/**
 * HTTPClient 工具类
 * 
 * @author Lynch 2014-09-15
 */
@SuppressWarnings("deprecation")
public class HTTPClientUtils {
	
	private static JsonNodeFactory factory = JsonNodeFactory.instance;

	/**
	 * Send SSL Request
	 * 
	 * @return
	 */
	public static ObjectNode sendHTTPRequest(URL url, Object credentail, Object dataBody, String method) {
		HttpClient httpClient = null;
		try {
			httpClient = getClient(true);
			
			HttpResponse response = null;
			if (method.equals(HTTPMethod.METHOD_POST)) {
				HttpPost httpPost = new HttpPost(url.toURI());
				Token.applyAuthentication(httpPost, credentail);
				httpPost.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));
				response = httpClient.execute(httpPost);
			} else if (method.equals(HTTPMethod.METHOD_PUT)) {
				HttpPut httpPut = new HttpPut(url.toURI());
				Token.applyAuthentication(httpPut, credentail);
				httpPut.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));
				response = httpClient.execute(httpPut);
			} else if (method.equals(HTTPMethod.METHOD_GET)) {
				HttpGet httpGet = new HttpGet(url.toURI());
				Token.applyAuthentication(httpGet, credentail);
				response = httpClient.execute(httpGet);
			} else if (method.equals(HTTPMethod.METHOD_DELETE)) {
				HttpDelete httpDelete = new HttpDelete(url.toURI());
				Token.applyAuthentication(httpDelete, credentail);
				response = httpClient.execute(httpDelete);
			}
			HttpEntity entity = response.getEntity();
			ObjectNode resObjectNode = factory.objectNode();
			if (null != entity) {
				String responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				
				ObjectMapper mapper = new ObjectMapper();
				resObjectNode = mapper.readTree(mapper.getJsonFactory().createJsonParser(responseContent));
				resObjectNode.put("statusCode", response.getStatusLine().getStatusCode());
			}
			return resObjectNode;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != httpClient) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * DownLoadFile whit Jersey
	 * @return
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.KeyManagementException
	 * @throws java.io.IOException
	 */
	public static File downLoadFile(URL url, Credential credentail, List<NameValuePair> headers, File localPath) {
		HttpClient httpClient = null;
		FileOutputStream fos = null;
		InputStream in = null;
		try {
			httpClient = getClient(true);
			HttpGet httpGet = new HttpGet(url.toURI());
			
			if (credentail != null) {
				Token.applyAuthentication(httpGet, credentail);
			}
			for (NameValuePair header : headers) {
				httpGet.addHeader(header.getName(), header.getValue());
			}
			
			in = httpClient.execute(httpGet).getEntity().getContent();
			fos = new FileOutputStream(localPath);
			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = in.read(buffer)) != -1) {
				fos.write(buffer, 0, len1);
			}
			
			return localPath;
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * UploadFile whit Jersey
	 * 
	 * @return
	 */
	public static ObjectNode uploadFile(URL url, File file, Object credentail, List<NameValuePair> headers) throws RuntimeException {
		HttpClient httpClient = null;
		try {
			httpClient = getClient(true);
			
			HttpPost httpPost = new HttpPost(url.toURI());
			Token.applyAuthentication(httpPost, credentail);
			for (NameValuePair header : headers) {
				httpPost.addHeader(header.getName(), header.getValue());
			}
			
			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, MediaType.APPLICATION_OCTET_STREAM);
			mpEntity.addPart("file", cbFile);
			httpPost.setEntity(mpEntity);
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			ObjectNode resObjectNode = factory.objectNode();
			if (null != entity) {
				String responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				
				ObjectMapper mapper = new ObjectMapper();
				resObjectNode = mapper.readTree(mapper.getJsonFactory().createJsonParser(responseContent));
				resObjectNode.put("statusCode", response.getStatusLine().getStatusCode());
			}
			
			return resObjectNode;
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			if (null != httpClient) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * Create a httpClient instance
	 * 
	 * @param isSSL
	 * @return HttpClient instance
	 */
	public static HttpClient getClient(boolean isSSL) {
		HttpClient httpClient = new DefaultHttpClient();
		if (isSSL) {
			X509TrustManager xtm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			
			try {
				SSLContext ctx = SSLContext.getInstance("TLS");
				ctx.init(null, new TrustManager[] { xtm }, null);
				SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
				httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return httpClient;
	}

	public static URL getURL(String path) {
		URL url = null;
		try {
			url = new URL(Constants.API_HTTP_SCHEMA, Constants.API_SERVER_HOST, "/" + path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return url;
	}

	/**
	 * Check illegal String
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.lookingAt();
	}
}