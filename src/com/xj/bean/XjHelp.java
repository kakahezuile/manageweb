package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class XjHelp {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer helpId;
	
	@NotUpdataAnnotation
	private String emobIdFrom;
	@NotUpdataAnnotation
	private String emobIdTo;
	
	private Integer createTime;
	
	private Integer endTime;
	
	@NotUpdataAnnotation
	private String orderId;
	
	private String helpStatus = "normal";
	
	private Integer communityId;

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getHelpId() {
		return helpId;
	}

	public void setHelpId(Integer helpId) {
		this.helpId = helpId;
	}

	public String getEmobIdFrom() {
		return emobIdFrom;
	}

	public void setEmobIdFrom(String emobIdFrom) {
		this.emobIdFrom = emobIdFrom;
	}

	public String getEmobIdTo() {
		return emobIdTo;
	}

	public void setEmobIdTo(String emobIdTo) {
		this.emobIdTo = emobIdTo;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getHelpStatus() {
		return helpStatus;
	}

	public void setHelpStatus(String helpStatus) {
		this.helpStatus = helpStatus;
	}
}
