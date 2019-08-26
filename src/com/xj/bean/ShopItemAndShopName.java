package com.xj.bean;




import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;



/**
 * 
 */

public class ShopItemAndShopName {
	@NotInsertAnnotation
    private Long serviceId;
    private String serviceName;
    private Long shopId;
    private Long createTime;
    private String status;
  
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private Integer isUpdate;
    
    private Integer catId;
    
    private Integer brandId;
    
    private String serviceImg;
    
    private String shopName;
    
    private Integer stock; // 库存
    
    private String currentPrice; // 现价
    
    private String originPrice; // 原价
    
    private String score;
    
    private String shopEmobId;
    
    private String multiattribute; // 是否有子项
    
    private Integer purchase; // 限购量
    
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private List<ShopItem> list;
    
    private Integer attrId;
    
    private String attrName;

	public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public List<ShopItem> getList() {
		return list;
	}

	public void setList(List<ShopItem> list) {
		this.list = list;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public String getMultiattribute() {
		return multiattribute;
	}

	public void setMultiattribute(String multiattribute) {
		this.multiattribute = multiattribute;
	}

	public String getShopEmobId() {
		return shopEmobId;
	}

	public void setShopEmobId(String shopEmobId) {
		this.shopEmobId = shopEmobId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

  

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}



	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
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
	

	

    
}
