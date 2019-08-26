package com.xj.bean;

import java.io.Serializable;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

@SuppressWarnings("serial")
public class ShopType implements Serializable{
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer shopTypeId;
	
	private String shopTypeName;
	
	private Integer divType;
	
	private String inputId;
	
	public Integer getDivType() {
		return divType;
	}

	public void setDivType(Integer divType) {
		this.divType = divType;
	}

	public String getInputId() {
		return inputId;
	}

	public void setInputId(String inputId) {
		this.inputId = inputId;
	}

	public Integer getShopTypeId() {
		return shopTypeId;
	}

	public void setShopTypeId(Integer shopTypeId) {
		this.shopTypeId = shopTypeId;
	}

	public String getShopTypeName() {
		return shopTypeName;
	}

	public void setShopTypeName(String shopTypeName) {
		this.shopTypeName = shopTypeName;
	}
}
