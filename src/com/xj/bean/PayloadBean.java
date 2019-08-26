package com.xj.bean;


import java.util.Map;

public class PayloadBean {
	private BodiesBean bodies; // 消息集合
	
	private Map<String, Object> ext; // 自定义内容
	
	private String from;
	
	private String to;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}


	public BodiesBean getBodies() {
		return bodies;
	}

	public void setBodies(BodiesBean bodies) {
		this.bodies = bodies;
	}

	public Map<String, Object> getExt() {
		return ext;
	}

	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}
}
