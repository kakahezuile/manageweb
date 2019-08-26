package com.xj.bean;
/**
 * 帮帮币相关配置
 * @author Administrator
 *
 */
public class BonusCoinConfig {

	private BonuscoinExchange bonuscoinExchange;  //帮帮币兑换消费金额
	private BonuscoinManager bonuscoinManager;  //帮帮币管理 - 不同模块分享数量不同
	private BonuscoinRatio bonuscoinRatio;   //帮帮币消费得币
	private String status; //小区帮帮币功能是否开启
	private Integer communityId ;
	private String communityName ;
	public BonuscoinExchange getBonuscoinExchange() {
		return bonuscoinExchange;
	}
	public void setBonuscoinExchange(BonuscoinExchange bonuscoinExchange) {
		this.bonuscoinExchange = bonuscoinExchange;
	}
	public BonuscoinManager getBonuscoinManager() {
		return bonuscoinManager;
	}
	public void setBonuscoinManager(BonuscoinManager bonuscoinManager) {
		this.bonuscoinManager = bonuscoinManager;
	}
	public BonuscoinRatio getBonuscoinRatio() {
		return bonuscoinRatio;
	}
	public void setBonuscoinRatio(BonuscoinRatio bonuscoinRatio) {
		this.bonuscoinRatio = bonuscoinRatio;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	
}
