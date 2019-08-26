package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class StandardEntry {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer entryId;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String price;
	
	private String entrySum;
	
	private Integer standardId;
	
	private Integer communityId;
	
	private String sort;
	
	private Integer createTime;

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEntrySum() {
		return entrySum;
	}

	public void setEntrySum(String entrySum) {
		this.entrySum = entrySum;
	}

	public Integer getStandardId() {
		return standardId;
	}

	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}
}
