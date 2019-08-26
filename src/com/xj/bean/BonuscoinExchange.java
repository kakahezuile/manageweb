package com.xj.bean;

import java.math.BigDecimal;
import java.math.MathContext;

public class BonuscoinExchange {
	
	private Integer exchangeId;
	
	private Integer communityId; // 小区id
	
	private Integer bonuscoin; // 帮帮币
	
	private Integer exchange; // 兑换的金额
	
	private Integer serviceId ;  //模块
	

	public Integer getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getBonuscoin() {
		return bonuscoin;
	}

	public void setBonuscoin(Integer bonuscoin) {
		this.bonuscoin = bonuscoin;
	}

	public Integer getExchange() {
		return exchange;
	}

	public void setExchange(Integer exchange) {
		this.exchange = exchange;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Float getExchangeRatio() {
		return new BigDecimal(exchange).divide(new BigDecimal(bonuscoin)).round(new MathContext(2)).floatValue();
	}
}
