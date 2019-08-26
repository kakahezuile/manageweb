package com.xj.stat.po;

/**
 * App扫码下载统计结果
 * @author wangld
 */
public class AppDownloadStatistics {

	private String terrace;			//设备
	private Integer communityId;	//小区id
	private String communityName;	//小区name
	private String type;			//码的来源（类型）
	private String time;			//时间
	private Integer result;			//扫描次数

	public String getTerrace() {
		return terrace;
	}

	public void setTerrace(String terrace) {
		this.terrace = terrace;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
}