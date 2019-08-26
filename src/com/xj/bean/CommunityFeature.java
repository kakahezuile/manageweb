package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;

public class CommunityFeature {
	
	public CommunityFeature( String featureName, String featureType, String featureValue, Integer communityId, Integer expense, Integer expenseCoin,  Integer shareCoin, Integer exchangeCoin, Integer exchange) {
		super();
		this.featureName = featureName;
		this.featureType = featureType;
		this.featureValue = featureValue;
		this.communityId = communityId;
		this.expense = expense;
		this.expenseCoin = expenseCoin;
		this.shareCoin = shareCoin;
		this.exchangeCoin = exchangeCoin;
		this.exchange = exchange;
	}

	public CommunityFeature() {
		super();
	}

	@MyAnnotation
	@NotInsertAnnotation
    private Integer featureId;

	@MyAnnotation
    private String featureName;

	@MyAnnotation
    private String featureType;

    private String featureValue;

    private Integer communityId;

    private Integer expense;

    private Integer expenseCoin;

    private Float expenseRatio;

    private Integer shareCoin;

    private Integer exchangeCoin;

    private Integer exchange;

    private Float exchangeRatio;

    @MyAnnotation
    private Integer updateTime;
    
    @NotInsertAnnotation
    private String communityName;

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    public Integer getExpenseCoin() {
        return expenseCoin;
    }

    public void setExpenseCoin(Integer expenseCoin) {
        this.expenseCoin = expenseCoin;
    }


    public void setExpenseRatio(Float expenseRatio) {
        this.expenseRatio = expenseRatio;
    }

    public Integer getShareCoin() {
        return shareCoin;
    }

    public void setShareCoin(Integer shareCoin) {
        this.shareCoin = shareCoin;
    }

    public Integer getExchangeCoin() {
        return exchangeCoin;
    }

    public void setExchangeCoin(Integer exchangeCoin) {
        this.exchangeCoin = exchangeCoin;
    }

    public Integer getExchange() {
        return exchange;
    }

    public void setExchange(Integer exchange) {
        this.exchange = exchange;
    }

    public void setExchangeRatio(Float exchangeRatio) {
        this.exchangeRatio = exchangeRatio;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Float getExpenseRatio() {
		return expenseRatio;
	}

	public Float getExchangeRatio() {
		return exchangeRatio;
	}
}