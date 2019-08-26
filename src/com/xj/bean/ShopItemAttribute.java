package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class ShopItemAttribute {
	
	private String serviceName;
	
	private String currentPrice; // 现价
    
    private String originPrice; // 原价
    
    private String multiattribute; // 是否多属性  yes - 是   no - 不是
    
    private Integer brandId;
    
    private String serviceImg;
    
    private Integer catId;
    
    private Long createTime;
    
    private String status;
  
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private Integer isUpdate;
    
    private Long shopId;
    
    private Integer serviceId;
    
    private Integer level;
    
    private Integer stock;
    
    private Integer version;
    
    private Integer purchase; // 限购数量
    
    private Integer attrId;
    
    public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}

	private List<Attribute> list;

	public List<Attribute> getList() {
		return list;
	}

	public void setList(List<Attribute> list) {
		this.list = list;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

	public String getMultiattribute() {
		return multiattribute;
	}

	public void setMultiattribute(String multiattribute) {
		this.multiattribute = multiattribute;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getServiceImg() {
		return serviceImg;
	}

	public void setServiceImg(String serviceImg) {
		this.serviceImg = serviceImg;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
    
}
