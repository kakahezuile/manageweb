package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class ShopsCategory {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer shopsCategoryId;
	
	private Integer shopId;
	
	private Integer categoryId;
	
	private String status;
	
	private Integer createTime;
	

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getShopsCategoryId() {
		return shopsCategoryId;
	}

	public void setShopsCategoryId(Integer shopsCategoryId) {
		this.shopsCategoryId = shopsCategoryId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
