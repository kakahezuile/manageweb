package com.xj.bean;

public class QiNiuBody {
	private Integer qiNiuId; // 主键id
	
	private String qiNiuType; // 类型   users  shops  circle  诸如此类
	
	private String key; // 图片名
	
	private String hash;

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Integer getQiNiuId() {
		return qiNiuId;
	}

	public void setQiNiuId(Integer qiNiuId) {
		this.qiNiuId = qiNiuId;
	}

	public String getQiNiuType() {
		return qiNiuType;
	}

	public void setQiNiuType(String qiNiuType) {
		this.qiNiuType = qiNiuType;
	}
}
