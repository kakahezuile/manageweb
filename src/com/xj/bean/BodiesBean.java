package com.xj.bean;

public class BodiesBean {
	private String msg; //消息内容
	
	private String type; //消息类型。txt:文本消息, img:图片, loc：位置, audio：语音
	
	private int length; //语音时长，单位为秒，这个属性只有语音消息有
	
	private String url; //图片语音等文件的网络url，图片和语音消息有这个属性
	
	private String filename; //文件名字，图片和语音消息有这个属性
	
	private String secret; //获取文件的secret，图片和语音消息有这个属性
	
	private String lat; //发送的位置的纬度，只有位置消息有这个属性
	
	private String lng; //位置经度，只有位置消息有这个属性
	
	private String addr; //位置消息详细地址，只有位置消息有这个属性

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	
}
