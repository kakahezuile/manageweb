package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;



/**
 * 帮帮币兑换比例
 * @author Administrator
 *
 */
public class BonuscoinRatio {
	@NotInsertAnnotation
	@NotUpdataAnnotation
    private Integer ratiosId;

    private Integer communityId; // 小区id

    private Integer sort; // 店铺类型

    private Integer consume; // 消费金额

    private Integer exchange; // 兑换额度

    private Float proportion; // 兑换比例
 
    private Integer createTime; // 创建时间

    private Integer updateTime; // 更新时间

    private Integer adminId; // 管理员id

    public Integer getRatiosId() {
        return ratiosId;
    }

    public void setRatiosId(Integer ratiosId) {
        this.ratiosId = ratiosId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getConsume() {
        return consume;
    }

    public void setConsume(Integer consume) {
        this.consume = consume;
    }

    public Integer getExchange() {
        return exchange;
    }

    public void setExchange(Integer exchange) {
        this.exchange = exchange;
    }

    public Float getProportion() {
        return proportion;
    }

    public void setProportion(Float proportion) {
        this.proportion = proportion;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    

    public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}