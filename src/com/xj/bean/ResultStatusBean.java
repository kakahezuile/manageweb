package com.xj.bean;

import java.util.List;

/**
 * 返回结果包装类
 */
public class ResultStatusBean {
    private String status; // 状态 - yes or no   访问是否成功
    private String errorCode;
    private String message; // 消息
    private Integer resultId; // 如果为添加操作 则 为主键
    private Object info; // 消息体   -  结果为集合 则 list   结果为对象 则 object
    private String emobId; // 环信id
    private String cityName;
    private String communityName;
    private Integer createTime; // 创建时间 或者 最新时间
    
    private Integer startTime;
    
    private Integer endTime;
    
    private List<OrderResult> resultOrders; 
    
    private List<Integer> shopItemIds; // 批量添加商品时 返回主键集合
    
    private Object others;
    
//    private List<CommunityFeature> communityFeatures;//2015-7-21新增小区属性
//
//	public List<CommunityFeature> getCommunityFeatures() {
//		return communityFeatures;
//	}
//
//	public void setCommunityFeatures(List<CommunityFeature> communityFeatures) {
//		this.communityFeatures = communityFeatures;
//	}

	public List<Integer> getShopItemIds() {
		return shopItemIds;
	}

	public void setShopItemIds(List<Integer> shopItemIds) {
		this.shopItemIds = shopItemIds;
	}

	public List<OrderResult> getResultOrders() {
		return resultOrders;
	}

	public void setResultOrders(List<OrderResult> resultOrders) {
		this.resultOrders = resultOrders;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getOthers() {
		return others;
	}

	public void setOthers(Object others) {
		this.others = others;
	}
}
