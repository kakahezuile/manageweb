package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class ItemCategory {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer catId;
	
	private String catName;
	
	private String keywords;
	
	private String catDesc;
	
	private Integer sortOrder;
	
	private Integer createTime;
	
	private String shopType;
	
	private String imgName;
	
	private Integer communityId;
	
	private String categoryVersion;
	
	private Integer itemNumber; // 商品量
	
	private Integer updateTime;

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getCategoryVersion() {
		return categoryVersion;
	}

	public void setCategoryVersion(String categoryVersion) {
		this.categoryVersion = categoryVersion;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
