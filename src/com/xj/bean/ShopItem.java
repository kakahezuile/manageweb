package com.xj.bean;




import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;



/**
 * 商品表
 */

public class ShopItem {
	@NotInsertAnnotation
	@NotUpdataAnnotation
    private Long serviceId;
    private String serviceName; // 商品名称
    private Long shopId; // 店家id
    private Long createTime; // 创建时间
    private String status; // 店家类型
  
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private Integer isUpdate;
    
    private Integer catId; // 分类id
    
    private Integer brandId; // 现在只有  0 或 1    0为普通商品  1为特价商品
    
    private String serviceImg; // 商品图片
    
    private Integer stock; // 库存
    
    private String currentPrice; // 现价
    
    private String originPrice; // 原价
    
    @MyAnnotation
    private Integer level; // 根据这个字段做 排序
    
    private String multiattribute; // 是否多属性  yes - 是   no - 不是
    
    private Integer purchase; // 限购数量
    
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private List<ShopItem> list; // 子商品
    
    private Integer attrId; // 商品类型
    
    private String attrName; // 类型名
    
    private Integer updateTime;
    

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	private Integer version;

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



	

    
}
