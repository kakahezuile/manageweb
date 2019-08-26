package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class Express {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer expressId;
	
	private String expressName;
	
	private String expressGoods;
	
	private Integer expressResult;
	
	private Integer expressCollectionTime;
	
	private Integer expressCreateTime;
	
	private Integer expressStatus;
	
	private String expressNo;
	
	private String expressPhone;
	
	private Integer expressUserType;
	
	private Integer communityId;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String expressStationInform;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String expressMessageInform;

	public String getExpressStationInform() {
		return expressStationInform;
	}

	public void setExpressStationInform(String expressStationInform) {
		this.expressStationInform = expressStationInform;
	}

	public String getExpressMessageInform() {
		return expressMessageInform;
	}

	public void setExpressMessageInform(String expressMessageInform) {
		this.expressMessageInform = expressMessageInform;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressGoods() {
		return expressGoods;
	}

	public void setExpressGoods(String expressGoods) {
		this.expressGoods = expressGoods;
	}

	public Integer getExpressResult() {
		return expressResult;
	}

	public void setExpressResult(Integer expressResult) {
		this.expressResult = expressResult;
	}

	public Integer getExpressCollectionTime() {
		return expressCollectionTime;
	}

	public void setExpressCollectionTime(Integer expressCollectionTime) {
		this.expressCollectionTime = expressCollectionTime;
	}

	public Integer getExpressCreateTime() {
		return expressCreateTime;
	}

	public void setExpressCreateTime(Integer expressCreateTime) {
		this.expressCreateTime = expressCreateTime;
	}

	public Integer getExpressStatus() {
		return expressStatus;
	}

	public void setExpressStatus(Integer expressStatus) {
		this.expressStatus = expressStatus;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressPhone() {
		return expressPhone;
	}

	public void setExpressPhone(String expressPhone) {
		this.expressPhone = expressPhone;
	}

	public Integer getExpressUserType() {
		return expressUserType;
	}

	public void setExpressUserType(Integer expressUserType) {
		this.expressUserType = expressUserType;
	}
}
