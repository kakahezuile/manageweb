package com.xj.bean;


/**
 * 帮帮币管理 - 不同模块分享数量不同
 * @author Administrator
 *
 */
public class BonuscoinManager {
    private Integer bonuscoinManagerId;

    private Integer shopTypeId; // 店铺类型

    private Integer bonuscoinCount; // 数量
    
    private Integer communityId; 

    public Integer getBonuscoinManagerId() {
        return bonuscoinManagerId;
    }

    public void setBonuscoinManagerId(Integer bonuscoinManagerId) {
        this.bonuscoinManagerId = bonuscoinManagerId;
    }

    public Integer getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(Integer shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    public Integer getBonuscoinCount() {
        return bonuscoinCount;
    }

    public void setBonuscoinCount(Integer bonuscoinCount) {
        this.bonuscoinCount = bonuscoinCount;
    }

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
    
}